package ru.yandex.practicum.bank.service.account.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChangeBalanceDto {

    private BigDecimal amount;

    private Long version;
}
