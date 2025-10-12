package ru.yandex.practicum.bank.service.notification.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import ru.yandex.practicum.bank.common.massage.Mail;
import ru.yandex.practicum.bank.service.notification.dto.MailDto;

public interface MailService {
    void send(MailDto mailDto);
    void send(ConsumerRecord<String, Mail> mailConsumerRecord);
}
