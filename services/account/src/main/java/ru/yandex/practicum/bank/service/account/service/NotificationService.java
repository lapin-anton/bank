package ru.yandex.practicum.bank.service.account.service;

import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.account.dto.AccountDto;

public interface NotificationService {
    void openAccount(AccountDto accountDto, User user);
}
