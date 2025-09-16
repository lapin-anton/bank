package ru.yandex.practicum.bank.service.exchange.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.bank.service.exchange.model.Currency;
import ru.yandex.practicum.bank.service.exchange.model.ExchangeRate;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateDto {

    @NotNull
    private Currency currency;

    @NotNull
    private BigDecimal value;

    public static RateDto of(ExchangeRate rate) {
        RateDto dto = new RateDto();

        dto.currency = rate.getCurrency();
        dto.value = rate.getValue();

        return dto;
    }
}
