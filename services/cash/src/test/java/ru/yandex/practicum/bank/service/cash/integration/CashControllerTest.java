package ru.yandex.practicum.bank.service.cash.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.bank.client.account.api.AccountClient;
import ru.yandex.practicum.bank.client.account.model.AccountDto;
import ru.yandex.practicum.bank.client.account.model.AccountStatus;
import ru.yandex.practicum.bank.client.account.model.ChangeBalanceDto;
import ru.yandex.practicum.bank.client.account.model.Currency;
import ru.yandex.practicum.bank.client.blocker.api.BlockerClient;
import ru.yandex.practicum.bank.client.blocker.model.CashCheckDto;
import ru.yandex.practicum.bank.client.blocker.model.ResultCheckDto;
import ru.yandex.practicum.bank.client.notification.api.NotificationClient;
import ru.yandex.practicum.bank.service.cash.config.JwtTestConfig;
import ru.yandex.practicum.bank.service.cash.config.MockClientConfig;
import ru.yandex.practicum.bank.service.cash.dto.CashTransactionDto;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({ JwtTestConfig.class, MockClientConfig.class })
class CashControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private BlockerClient blockerClient;

    @Autowired
    private NotificationClient notificationClient;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer mock.jwt.token";

    @BeforeEach
    void setUpMocks() {
        AccountDto account = new AccountDto();
        account.setId(1L);
        account.setUserId("test-user-id");
        account.setNumber("ACC-123");
        account.setCurrency(Currency.RUB);
        account.setBalance(new BigDecimal("1000.00"));
        account.setStatus(AccountStatus.ACTIVE);
        account.setVersion(0L);

        Mockito.when(accountClient.getAccountByNumber(eq("ACC-123"))).thenReturn(account);
        Mockito.when(blockerClient.checkCash(any(CashCheckDto.class))).thenReturn(new ResultCheckDto(true));
        doNothing().when(accountClient).changeBalance(eq(1L), any(ChangeBalanceDto.class));
        doNothing().when(notificationClient).sendMail(any());
    }

    @Test
    void putCash_ShouldSucceed() throws Exception {
        CashTransactionDto dto = new CashTransactionDto();
        dto.setAccountNumber("ACC-123");
        dto.setAmount(new BigDecimal("200.00"));

        mockMvc.perform(put("/put")
                .header(AUTH_HEADER, BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void withdrawCash_ShouldSucceed() throws Exception {
        CashTransactionDto dto = new CashTransactionDto();
        dto.setAccountNumber("ACC-123");
        dto.setAmount(new BigDecimal("500.00"));

        mockMvc.perform(put("/withdraw")
                .header(AUTH_HEADER, BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
