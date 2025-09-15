package ru.yandex.practicum.bank.service.cash.service;

import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.cash.dto.CashTransactionDto;

public interface NotificationService {
    void notifyPutCash(CashTransactionDto transferDto, User user);

    void notifyWithdrawCash(CashTransactionDto transferDto, User user);
}
