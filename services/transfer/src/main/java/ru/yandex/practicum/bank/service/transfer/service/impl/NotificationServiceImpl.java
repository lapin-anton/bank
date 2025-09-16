package ru.yandex.practicum.bank.service.transfer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.client.notification.api.NotificationClient;
import ru.yandex.practicum.bank.client.notification.model.MailDto;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.transfer.dto.TransferDto;
import ru.yandex.practicum.bank.service.transfer.service.NotificationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationClient notificationClient;

    @Override
    public void notify(TransferDto transferDto, User user) {

        MailDto mailDto = new MailDto();

        mailDto.setEmail(user.getEmail());
        mailDto.setSubject("Перевод исполнен");
        mailDto.setText("Перевод с счета %s на счет %s исполнен".formatted(transferDto.getFromAccount(),
                transferDto.getToAccount()));

        notificationClient.sendMail(mailDto);
    }
}
