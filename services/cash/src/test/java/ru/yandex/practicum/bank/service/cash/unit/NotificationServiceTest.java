package ru.yandex.practicum.bank.service.cash.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.client.notification.api.NotificationClient;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.cash.dto.CashTransactionDto;
import ru.yandex.practicum.bank.service.cash.service.NotificationService;
import ru.yandex.practicum.bank.service.cash.service.impl.NotificationServiceImpl;

import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = NotificationServiceTest.Config.class)
class NotificationServiceTest {

    @Configuration
    static class Config {
        @Bean
        NotificationClient notificationClient() {
            return mock(NotificationClient.class);
        }

        @Bean
        NotificationService notificationService(NotificationClient client) {
            return new NotificationServiceImpl(client);
        }
    }

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private NotificationService notificationService;

    private CashTransactionDto dto;
    private User user;

    @BeforeEach
    void setup() {
        reset(notificationClient);

        dto = new CashTransactionDto();
        dto.setAccountNumber("ACC123");

        user = new User();
        user.setEmail("user@example.com");
    }

    @Test
    void notifyPutCash_ShouldSendMail() {
        notificationService.notifyPutCash(dto, user);

        verify(notificationClient).sendMail(argThat(mail -> mail.getEmail().equals("user@example.com")
                && mail.getSubject().equals("Пополнение исполнено")
                && mail.getText().equals("Пополнение счета ACC123 исполнено")));
    }

    @Test
    void notifyWithdrawCash_ShouldSendMail() {
        notificationService.notifyWithdrawCash(dto, user);

        verify(notificationClient).sendMail(argThat(mail -> mail.getEmail().equals("user@example.com")
                && mail.getSubject().equals("Вывод средств исполнен")
                && mail.getText().equals("Вывод средств исполнено со счета ACC123")));
    }
}
