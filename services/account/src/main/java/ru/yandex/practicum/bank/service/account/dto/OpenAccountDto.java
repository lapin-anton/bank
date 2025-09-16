package ru.yandex.practicum.bank.service.account.dto;

import lombok.Data;
import ru.yandex.practicum.bank.service.account.model.Currency;

@Data
public class OpenAccountDto {
    private Currency currency;
}
