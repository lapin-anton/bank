package ru.yandex.practicum.bank.service.transfer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.common.config.KafkaConfig;
import ru.yandex.practicum.bank.common.massage.Mail;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.transfer.dto.TransferDto;
import ru.yandex.practicum.bank.service.transfer.service.NotificationService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final KafkaTemplate<String, Mail> mailKafkaTemplate;

    @Override
    public void notify(TransferDto transferDto, User user) {

        Mail mail = Mail.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .subject("Перевод исполнен")
                .text("Перевод с счета %s на счет %s исполнен".formatted(transferDto.getFromAccount(), transferDto.getToAccount()))
                .build();

        mailKafkaTemplate.send(KafkaConfig.MAIL_TOPIC, mail.getId().toString(), mail);
    }
}
