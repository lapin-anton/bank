package ru.yandex.practicum.bank.service.exchange.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.bank.service.exchange.model.Currency;

import java.math.BigDecimal;

@Data
public class ConvertRequestDto {

    @NotNull
    private Currency fromCurrency;

    @NotNull
    private Currency toCurrency;

    @NotNull
    private BigDecimal amount;
}
