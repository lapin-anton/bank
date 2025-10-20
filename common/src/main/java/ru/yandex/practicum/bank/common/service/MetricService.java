package ru.yandex.practicum.bank.common.service;

public interface MetricService {

    void recordLoginSuccess(String login);

    void recordLoginFailure(String login);

    void recordTransferFailure(String from, String to);

    void recordSuspiciousTransferBlocked(String to);

    void recordNotificationFailure(String to, String reason);

    void recordCurrencyRateUpdated();
}
