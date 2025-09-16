package ru.yandex.practicum.bank.service.account.model;

import lombok.Getter;

@Getter
public enum AccountStatus {
    ACTIVE("Активен"),
    BLOCKED("Заблокирован"),
    CLOSED("Закрыт");

    private final String title;

    AccountStatus(String title) {
        this.title = title;
    }
}
