package ru.yandex.practicum.bank.service.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.common.config.KafkaConfig;
import ru.yandex.practicum.bank.common.massage.Mail;
import ru.yandex.practicum.bank.common.service.MetricService;
import ru.yandex.practicum.bank.service.notification.dto.MailDto;
import ru.yandex.practicum.bank.service.notification.service.MailService;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final MetricService metricService;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void send(MailDto mailDto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailDto.getEmail());
            message.setSubject(mailDto.getSubject());
            message.setText(mailDto.getText());
            message.setFrom(from);
            mailSender.send(message);
        } catch (Exception e) {
            metricService.recordNotificationFailure(mailDto.getEmail(), e.getMessage());
        }
    }

    @Override
    @KafkaListener(
            groupId = KafkaConfig.MAIL_GROPE,
            topics = KafkaConfig.MAIL_TOPIC
    )
    public void send(ConsumerRecord<String, Mail> mailConsumerRecord) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailConsumerRecord.value().getEmail());
            message.setSubject(mailConsumerRecord.value().getSubject());
            message.setText(mailConsumerRecord.value().getText());
            message.setFrom(from);
            mailSender.send(message);
        } catch (Exception e) {
            metricService.recordNotificationFailure(mailConsumerRecord.value().getEmail(), e.getMessage());
        }
    }
}
