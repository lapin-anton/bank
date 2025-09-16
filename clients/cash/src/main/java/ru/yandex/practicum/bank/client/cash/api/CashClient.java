package ru.yandex.practicum.bank.client.cash.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.bank.client.cash.model.CashTransactionDto;
import ru.yandex.practicum.bank.common.resilience.ResilienceExecutor;

@Component
@RequiredArgsConstructor
@Slf4j
public class CashClient {

    private final CashApi cashApi;

    private final ResilienceExecutor resilience;

    public void putCash(CashTransactionDto dto) {
        resilience.execute(
                () -> {
                    cashApi.putCash(dto);
                    return null;
                },
                () -> {
                    log.warn("Fallback triggered: failed to put cash for account={}", dto.getAccountNumber());
                    return null;
                });
    }

    public void withdrawCash(CashTransactionDto dto) {
        resilience.execute(
                () -> {
                    cashApi.withdrawCash(dto);
                    return null;
                },
                () -> {
                    log.warn("Fallback triggered: failed to withdraw cash for account={}", dto.getAccountNumber());
                    return null;
                });
    }
}
