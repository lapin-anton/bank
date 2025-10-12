package ru.yandex.practicum.bank.ui.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.yandex.practicum.bank.client.account.api.AccountClient;
import ru.yandex.practicum.bank.client.cash.api.CashClient;
import ru.yandex.practicum.bank.client.transfer.api.TransferClient;

@TestConfiguration
public class MockClientConfig {

    @Primary
    @Bean(name = "mockAccountClient")
    public AccountClient accountClient() {
        return Mockito.mock(AccountClient.class);
    }

    @Primary
    @Bean(name = "mockCashClient")
    public CashClient cashClient() {
        return Mockito.mock(CashClient.class);
    }

    @Primary
    @Bean(name = "mockTransferClient")
    public TransferClient transferClient() {
        return Mockito.mock(TransferClient.class);
    }
}