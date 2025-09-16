package ru.yandex.practicum.bank.service.blocker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferCheckDto {

    @NotNull
    private String fromAccount;

    @NotNull
    private String toAccount;

    @NotNull
    private BigDecimal amount;
}
