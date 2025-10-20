package ru.yandex.practicum.bank.service.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.bank.common.config.KafkaConfig;
import ru.yandex.practicum.bank.common.massage.Mail;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.account.dto.AccountDto;
import ru.yandex.practicum.bank.service.account.service.NotificationService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final KafkaTemplate<String, Mail> mailKafkaTemplate;

    @Override
    public void openAccount(AccountDto accountDto, User user) {
        Mail mail = Mail.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .subject("Счет успешно открыт")
                .text("Открыт счет %s в валюте %s".formatted(accountDto.getNumber(), accountDto.getCurrency().name()))
                .build();

        mailKafkaTemplate.send(KafkaConfig.MAIL_TOPIC, mail.getId().toString(), mail);
    }
}
