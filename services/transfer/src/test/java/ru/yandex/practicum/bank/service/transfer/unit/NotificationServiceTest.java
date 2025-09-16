package ru.yandex.practicum.bank.service.transfer.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.client.notification.api.NotificationClient;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.transfer.dto.TransferDto;
import ru.yandex.practicum.bank.service.transfer.service.NotificationService;
import ru.yandex.practicum.bank.service.transfer.service.impl.NotificationServiceImpl;

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
    private NotificationService notificationService;

    @Autowired
    private NotificationClient notificationClient;

    @BeforeEach
    void setup() {
        reset(notificationClient);
    }

    @Test
    void notify_ShouldSendCorrectMail() {
        User user = new User();
        user.setEmail("test@bank.com");

        TransferDto dto = new TransferDto();
        dto.setFromAccount("ACC-111");
        dto.setToAccount("ACC-222");

        notificationService.notify(dto, user);

        verify(notificationClient).sendMail(argThat(mail -> mail.getEmail().equals("test@bank.com") &&
                mail.getSubject().equals("Перевод исполнен") &&
                mail.getText().equals("Перевод с счета ACC-111 на счет ACC-222 исполнен")));
    }
}
