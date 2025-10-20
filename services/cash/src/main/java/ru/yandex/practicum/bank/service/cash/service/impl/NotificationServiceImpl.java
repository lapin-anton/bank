package ru.yandex.practicum.bank.service.cash.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.common.config.KafkaConfig;
import ru.yandex.practicum.bank.common.massage.Mail;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.cash.dto.CashTransactionDto;
import ru.yandex.practicum.bank.service.cash.service.NotificationService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final KafkaTemplate<String, Mail> mailKafkaTemplate;

    @Override
    public void notifyPutCash(CashTransactionDto transferDto, User user) {
        Mail mail = Mail.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .subject("Пополнение исполнено")
                .text("Пополнение счета %s исполнено".formatted(transferDto.getAccountNumber()))
                .build();

        mailKafkaTemplate.send(KafkaConfig.MAIL_TOPIC, mail.getId().toString(), mail);
    }

    @Override
    public void notifyWithdrawCash(CashTransactionDto transferDto, User user) {
        Mail mail = Mail.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .subject("Вывод средств исполнен")
                .text("Вывод средств исполнено со счета %s".formatted(transferDto.getAccountNumber()))
                .build();

        mailKafkaTemplate.send(KafkaConfig.MAIL_TOPIC, mail.getId().toString(), mail);
    }
}
