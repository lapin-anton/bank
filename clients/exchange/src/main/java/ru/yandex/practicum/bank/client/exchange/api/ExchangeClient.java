package ru.yandex.practicum.bank.client.exchange.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.bank.client.exchange.model.ConvertRequestDto;
import ru.yandex.practicum.bank.client.exchange.model.ConvertResponseDto;
import ru.yandex.practicum.bank.client.exchange.model.RateDto;
import ru.yandex.practicum.bank.client.exchange.model.UpdateExchangeRate200Response;
import ru.yandex.practicum.bank.common.resilience.ResilienceExecutor;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExchangeClient {

    private final ExchangeApi exchangeApi;

    private final ResilienceExecutor resilience;

    public ConvertResponseDto convertCurrency(ConvertRequestDto dto) {
        return resilience.execute(
                () -> exchangeApi.convertCurrency(dto),
                () -> {
                    log.warn("Fallback: failed to convert from {} to {}", dto.getFromCurrency(), dto.getToCurrency());
                    return null;
                });
    }

    public List<RateDto> getExchangeRates() {
        return resilience.execute(
                exchangeApi::getExchangeRates,
                () -> {
                    log.warn("Fallback: failed to get exchange rates");
                    return Collections.emptyList();
                });
    }

    public UpdateExchangeRate200Response updateExchangeRate(RateDto rateDto) {
        return resilience.execute(
                () -> exchangeApi.updateExchangeRate(rateDto),
                () -> {
                    log.warn("Fallback: failed to update exchange rate {} -> {}", rateDto.getCurrency(),
                            rateDto.getValue());
                    return fallbackRateUpdated(rateDto);
                });
    }

    private UpdateExchangeRate200Response fallbackRateUpdated(RateDto dto) {
        return new UpdateExchangeRate200Response();
    }
}
