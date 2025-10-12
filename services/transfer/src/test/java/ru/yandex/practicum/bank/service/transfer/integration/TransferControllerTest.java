package ru.yandex.practicum.bank.service.transfer.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.bank.client.account.api.AccountClient;
import ru.yandex.practicum.bank.client.account.model.AccountDto;
import ru.yandex.practicum.bank.client.account.model.AccountStatus;
import ru.yandex.practicum.bank.client.account.model.ChangeBalanceDto;
import ru.yandex.practicum.bank.client.account.model.Currency;
import ru.yandex.practicum.bank.client.blocker.api.BlockerClient;
import ru.yandex.practicum.bank.client.blocker.model.ResultCheckDto;
import ru.yandex.practicum.bank.client.blocker.model.TransferCheckDto;
import ru.yandex.practicum.bank.client.exchange.api.ExchangeClient;
import ru.yandex.practicum.bank.client.exchange.model.ConvertRequestDto;
import ru.yandex.practicum.bank.client.exchange.model.ConvertResponseDto;
import ru.yandex.practicum.bank.common.config.KafkaConfig;
import ru.yandex.practicum.bank.service.transfer.config.JwtTestConfig;
import ru.yandex.practicum.bank.service.transfer.config.MockClientConfig;
import ru.yandex.practicum.bank.service.transfer.dto.TransferDto;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({JwtTestConfig.class, MockClientConfig.class})
@EmbeddedKafka(partitions = 1, topics = {KafkaConfig.MAIL_TOPIC}, brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092",
        "port=9092"
})
@DirtiesContext
class TransferControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AccountClient accountClient;
    @Autowired private ExchangeClient exchangeClient;
    @Autowired private BlockerClient blockerClient;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer mock.jwt.token";

    @BeforeEach
    void setUpMocks() {
        AccountDto from = new AccountDto();
        from.setId(1L);
        from.setUserId("test-user-id");
        from.setNumber("FROM-ACC");
        from.setBalance(new BigDecimal("1000.00"));
        from.setCurrency(Currency.USD);
        from.setStatus(AccountStatus.ACTIVE);
        from.setVersion(1L);

        AccountDto to = new AccountDto();
        to.setId(2L);
        to.setUserId("other-user-id");
        to.setNumber("TO-ACC");
        to.setBalance(new BigDecimal("500.00"));
        to.setCurrency(Currency.USD);
        to.setStatus(AccountStatus.ACTIVE);
        to.setVersion(1L);

        Mockito.when(accountClient.getAccountByNumber("FROM-ACC")).thenReturn(from);
        Mockito.when(accountClient.getAccountByNumber("TO-ACC")).thenReturn(to);
        Mockito.when(blockerClient.checkTransfer(any(TransferCheckDto.class)))
                .thenReturn(new ResultCheckDto(true));
        Mockito.when(exchangeClient.convertCurrency(any(ConvertRequestDto.class)))
                .thenReturn(new ConvertResponseDto(new BigDecimal("100.00")));
        doNothing().when(accountClient).changeBalance(eq(1L), any(ChangeBalanceDto.class));
        doNothing().when(accountClient).changeBalance(eq(2L), any(ChangeBalanceDto.class));
    }

    @Test
    void transfer_ShouldSucceed() throws Exception {
        TransferDto dto = new TransferDto();
        dto.setFromAccount("FROM-ACC");
        dto.setToAccount("TO-ACC");
        dto.setAmount(new BigDecimal("100.00"));

        mockMvc.perform(post("/")
                        .header(AUTH_HEADER, BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void transfer_ShouldFail_WhenInsufficientFunds() throws Exception {
        AccountDto from = accountClient.getAccountByNumber("FROM-ACC");
        from.setBalance(new BigDecimal("50.00"));
        Mockito.when(accountClient.getAccountByNumber("FROM-ACC")).thenReturn(from);

        TransferDto dto = new TransferDto();
        dto.setFromAccount("FROM-ACC");
        dto.setToAccount("TO-ACC");
        dto.setAmount(new BigDecimal("100.00"));

        mockMvc.perform(post("/")
                        .header(AUTH_HEADER, BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError());
    }
}
