package ru.yandex.practicum.bank.service.notification.service;

import ru.yandex.practicum.bank.service.notification.dto.MailDto;

public interface MailService {
    void send(MailDto mailDto);
}
