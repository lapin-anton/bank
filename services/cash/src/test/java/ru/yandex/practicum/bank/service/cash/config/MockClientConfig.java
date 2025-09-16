package ru.yandex.practicum.bank.service.cash.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.yandex.practicum.bank.client.account.api.AccountClient;
import ru.yandex.practicum.bank.client.blocker.api.BlockerClient;
import ru.yandex.practicum.bank.client.notification.api.NotificationClient;

@TestConfiguration
public class MockClientConfig {

    @Bean
    public AccountClient accountClient() {
        return Mockito.mock(AccountClient.class);
    }

    @Bean
    public BlockerClient blockerClient() {
        return Mockito.mock(BlockerClient.class);
    }

    @Bean
    public NotificationClient notificationClient() {
        return Mockito.mock(NotificationClient.class);
    }
}
