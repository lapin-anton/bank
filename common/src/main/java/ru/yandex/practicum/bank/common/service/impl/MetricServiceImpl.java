package ru.yandex.practicum.bank.common.service.impl;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.common.service.MetricService;

@Service
@RequiredArgsConstructor
public class MetricServiceImpl implements MetricService {

    private final MeterRegistry meterRegistry;

    @Override
    public void recordLoginSuccess(String login) {
        meterRegistry.counter("custom_login_success_total", "login", login).increment();
    }

    @Override
    public void recordLoginFailure(String login) {
        meterRegistry.counter("custom_login_failure_total", "login", login).increment();
    }

    @Override
    public void recordTransferFailure(String from, String to) {
        meterRegistry.counter("custom_transfer_failure_total",
                "from", from, "to", to).increment();
    }

    @Override
    public void recordSuspiciousTransferBlocked(String to) {
        meterRegistry.counter("custom_transfer_blocked_total", "to", to).increment();
    }

    @Override
    public void recordNotificationFailure(String login, String reason) {
        meterRegistry.counter("custom_notification_failure_total", "login", login, "reason", reason).increment();
    }

    @Override
    public void recordCurrencyRateUpdated() {
        meterRegistry.counter("currency_rates_updated_total").increment();
    }
}
