package ru.yandex.practicum.bank.service.transfer.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.common.config.KafkaConfig;
import ru.yandex.practicum.bank.common.massage.Mail;
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
        public KafkaTemplate<String, Mail> kafkaTemplate() {
            return mock(KafkaTemplate.class);
        }

        @Bean
        public NotificationService notificationService(KafkaTemplate<String, Mail> kafkaTemplate) {
            return new NotificationServiceImpl(kafkaTemplate);
        }
    }

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private KafkaTemplate<String, Mail> kafkaTemplate;

    @BeforeEach
    void setup() {
        reset(kafkaTemplate);
    }

    @Test
    void notify_ShouldSendCorrectMail() {
        User user = new User();
        user.setEmail("user@example.com");

        TransferDto dto = new TransferDto();
        dto.setFromAccount("ACC-111");
        dto.setToAccount("ACC-222");

        notificationService.notify(dto, user);

        verify(kafkaTemplate).send(
                eq(KafkaConfig.MAIL_TOPIC),
                anyString(),
                argThat(mail ->
                        mail.getEmail().equals("user@example.com") &&
                                mail.getSubject().equals("Перевод исполнен") &&
                                mail.getText().equals("Перевод с счета ACC-111 на счет ACC-222 исполнен")
                )
        );
    }
}
