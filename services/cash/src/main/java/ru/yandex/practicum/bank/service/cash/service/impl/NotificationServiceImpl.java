package ru.yandex.practicum.bank.service.cash.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.client.notification.api.NotificationClient;
import ru.yandex.practicum.bank.client.notification.model.MailDto;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.cash.dto.CashTransactionDto;
import ru.yandex.practicum.bank.service.cash.service.NotificationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationClient notificationClient;

    @Override
    public void notifyPutCash(CashTransactionDto transferDto, User user) {
        MailDto mailDto = new MailDto();

        mailDto.setEmail(user.getEmail());
        mailDto.setSubject("Пополнение исполнено");
        mailDto.setText("Пополнение счета %s исполнено".formatted(transferDto.getAccountNumber()));

        notificationClient.sendMail(mailDto);
    }

    @Override
    public void notifyWithdrawCash(CashTransactionDto transferDto, User user) {
        MailDto mailDto = new MailDto();

        mailDto.setEmail(user.getEmail());
        mailDto.setSubject("Вывод средств исполнен");
        mailDto.setText("Вывод средств исполнено со счета %s".formatted(transferDto.getAccountNumber()));

        notificationClient.sendMail(mailDto);
    }
}
