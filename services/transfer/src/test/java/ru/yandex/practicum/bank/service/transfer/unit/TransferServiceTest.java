package ru.yandex.practicum.bank.service.transfer.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.client.account.api.AccountClient;
import ru.yandex.practicum.bank.client.account.model.AccountDto;
import ru.yandex.practicum.bank.client.account.model.ChangeBalanceDto;
import ru.yandex.practicum.bank.client.blocker.api.BlockerClient;
import ru.yandex.practicum.bank.client.blocker.model.ResultCheckDto;
import ru.yandex.practicum.bank.client.blocker.model.TransferCheckDto;
import ru.yandex.practicum.bank.client.exchange.api.ExchangeClient;
import ru.yandex.practicum.bank.client.exchange.model.ConvertResponseDto;
import org.springframework.security.access.AccessDeniedException;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.common.service.MetricService;
import ru.yandex.practicum.bank.service.transfer.dto.TransferDto;
import ru.yandex.practicum.bank.service.transfer.service.NotificationService;
import ru.yandex.practicum.bank.service.transfer.service.TransferService;
import ru.yandex.practicum.bank.service.transfer.service.impl.TransferServiceImpl;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = TransferServiceTest.Config.class)
class TransferServiceTest {

    @Configuration
    static class Config {
        @Bean
        AccountClient accountClient() {
            return mock(AccountClient.class);
        }

        @Bean
        ExchangeClient exchangeClient() {
            return mock(ExchangeClient.class);
        }

        @Bean
        BlockerClient blockerClient() {
            return mock(BlockerClient.class);
        }

        @Bean
        MetricService metricService() {
            return mock(MetricService.class);
        }

        @Bean
        NotificationService notificationService() {
            return mock(NotificationService.class);
        }

        @Bean
        TransferService transferService(AccountClient ac, ExchangeClient ec, BlockerClient bc, MetricService metricService, NotificationService notificationService) {
            return new TransferServiceImpl(ac, ec, bc, metricService, notificationService);
        }
    }

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private ExchangeClient exchangeClient;

    @Autowired
    private BlockerClient blockerClient;

    private final User user = new User();
    private final TransferDto dto = new TransferDto();
    private final AccountDto from = new AccountDto();
    private final AccountDto to = new AccountDto();

    @BeforeEach
    void setup() {
        reset(accountClient, exchangeClient, blockerClient);

        user.setId("user-123");

        dto.setFromAccount("from-acc");
        dto.setToAccount("to-acc");
        dto.setAmount(BigDecimal.valueOf(100));

        from.setId(1L);
        from.setUserId("user-123");
        from.setBalance(BigDecimal.valueOf(500));
        from.setVersion(1L);
        from.setCurrency(ru.yandex.practicum.bank.client.account.model.Currency.RUB);

        to.setId(2L);
        to.setUserId("other-user");
        to.setBalance(BigDecimal.valueOf(200));
        to.setVersion(2L);
        to.setCurrency(ru.yandex.practicum.bank.client.account.model.Currency.RUB);

        ResultCheckDto checkResult = new ResultCheckDto();
        checkResult.setResult(true);

        when(accountClient.getAccountByNumber("from-acc")).thenReturn(from);
        when(accountClient.getAccountByNumber("to-acc")).thenReturn(to);
        when(blockerClient.checkTransfer(any(TransferCheckDto.class))).thenReturn(checkResult);

        ConvertResponseDto conversion = new ConvertResponseDto();
        conversion.setResult(dto.getAmount());

        when(exchangeClient.convertCurrency(any())).thenReturn(conversion);
    }

    @Test
    void transfer_ShouldTransferMoneyCorrectly() {
        transferService.transfer(dto, user);

        verify(accountClient).changeBalance(eq(1L), argThat(change ->
                change.getAmount().compareTo(BigDecimal.valueOf(400)) == 0 &&
                        Objects.equals(change.getVersion(), 1L)
        ));

        verify(accountClient).changeBalance(eq(2L), argThat(change ->
                change.getAmount().compareTo(BigDecimal.valueOf(300)) == 0 &&
                        Objects.equals(change.getVersion(), 2L)
        ));
    }

    @Test
    void transfer_ShouldThrow_WhenNotOwner() {
        from.setUserId("not-this-user");

        assertThrows(AccessDeniedException.class, () -> transferService.transfer(dto, user));
    }

    @Test
    void transfer_ShouldThrow_WhenInsufficientFunds() {
        from.setBalance(BigDecimal.valueOf(50));

        assertThrows(IllegalStateException.class, () -> transferService.transfer(dto, user));
    }

    @Test
    void transfer_ShouldThrow_WhenBlocked() {
        ResultCheckDto denied = new ResultCheckDto();
        denied.setResult(false);
        when(blockerClient.checkTransfer(any())).thenReturn(denied);

        assertThrows(AccessDeniedException.class, () -> transferService.transfer(dto, user));
    }

    @Test
    void transfer_ShouldRollback_WhenToAccountChangeFails() {
        doThrow(new RuntimeException("fail to update TO")).when(accountClient)
                .changeBalance(eq(2L), any(ChangeBalanceDto.class));

        AccountDto updatedFrom = new AccountDto();
        updatedFrom.setId(1L);
        updatedFrom.setUserId("user-123");
        updatedFrom.setBalance(BigDecimal.valueOf(400));
        updatedFrom.setVersion(2L);
        updatedFrom.setCurrency(ru.yandex.practicum.bank.client.account.model.Currency.RUB);

        when(accountClient.getAccountByNumber("from-acc")).thenReturn(from).thenReturn(updatedFrom);

        assertThrows(RuntimeException.class, () -> transferService.transfer(dto, user));

        verify(accountClient).changeBalance(eq(1L), argThat(change ->
                change.getAmount().compareTo(BigDecimal.valueOf(500)) == 0 &&
                        Objects.equals(change.getVersion(), 2L)
        ));
    }
}
