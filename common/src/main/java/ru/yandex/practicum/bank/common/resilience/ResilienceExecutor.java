package ru.yandex.practicum.bank.common.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResilienceExecutor {

    private final CircuitBreaker circuitBreaker;
    private final Retry retry;
    private final RateLimiter rateLimiter;

    public <T> T execute(Supplier<T> supplier, Supplier<T> fallback) {
        Supplier<T> decoratedSupplier = Decorators
                .ofSupplier(supplier)
                .withRateLimiter(rateLimiter)
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry)
                .withFallback(throwable -> {
                    log.error(throwable.getMessage(), throwable);
                    return fallback.get();
                })
                .decorate();

        return decoratedSupplier.get();
    }
}
