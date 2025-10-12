package ru.yandex.practicum.bank.service.cash.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.common.config.KafkaConfig;
import ru.yandex.practicum.bank.common.message.Mail;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.cash.dto.CashTransactionDto;
import ru.yandex.practicum.bank.service.cash.service.NotificationService;
import ru.yandex.practicum.bank.service.cash.service.impl.NotificationServiceImpl;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


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
    private KafkaTemplate<String, Mail> kafkaTemplate;

    @Autowired
    private NotificationService notificationService;

    private CashTransactionDto dto;
    private User user;

    @BeforeEach
    void setup() {
        dto = new CashTransactionDto();
        dto.setAccountNumber("ACC123");

        user = new User();
        user.setEmail("user@example.com");
    }

    @Test
    void notifyPutCash_ShouldSendKafkaMessage() {
        notificationService.notifyPutCash(dto, user);

        verify(kafkaTemplate).send(
                eq(KafkaConfig.MAIL_TOPIC),
                anyString(),
                argThat(mail ->
                        mail.getEmail().equals("user@example.com") &&
                                mail.getSubject().equals("Пополнение исполнено") &&
                                mail.getText().equals("Пополнение счета ACC123 исполнено")
                )
        );
    }


    @Test
    void notifyWithdrawCash_ShouldSendKafkaMessage() {
        notificationService.notifyWithdrawCash(dto, user);

        verify(kafkaTemplate).send(
                eq(KafkaConfig.MAIL_TOPIC),
                anyString(),
                argThat(mail ->
                        mail.getEmail().equals("user@example.com") &&
                                mail.getSubject().equals("Вывод средств исполнен") &&
                                mail.getText().equals("Вывод средств исполнено со счета ACC123")
                )
        );
    }
}
