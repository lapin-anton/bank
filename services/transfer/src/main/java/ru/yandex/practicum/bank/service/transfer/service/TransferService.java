package ru.yandex.practicum.bank.service.transfer.service;

import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.transfer.dto.TransferDto;

public interface TransferService {
    void transfer(TransferDto transferDto, User user);
}
