package ru.yandex.practicum.bank.ui.service;

import ru.yandex.practicum.bank.ui.dto.PasswordUserFormDto;
import ru.yandex.practicum.bank.ui.dto.UserFormDto;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.ui.dto.UserItemDto;

import java.util.List;

public interface UserService {
    void edit(User user, UserFormDto userFormDto);

    void password(User user, PasswordUserFormDto passwordUserFormDto);

    List<UserItemDto> getAllOverUsers(User user);
}
