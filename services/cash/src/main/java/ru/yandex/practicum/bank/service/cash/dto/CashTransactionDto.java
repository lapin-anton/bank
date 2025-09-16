package ru.yandex.practicum.bank.service.cash.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashTransactionDto {

    @NotEmpty
    private String accountNumber;

    @NotNull
    private BigDecimal amount;
}
