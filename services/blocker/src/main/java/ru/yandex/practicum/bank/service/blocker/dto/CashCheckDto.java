package ru.yandex.practicum.bank.service.blocker.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashCheckDto {

    @NotEmpty
    private String accountNumber;

    @NotNull
    private BigDecimal amount;
}
