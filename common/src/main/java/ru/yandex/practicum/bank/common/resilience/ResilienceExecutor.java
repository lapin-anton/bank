package ru.yandex.practicum.bank.common.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.decorators.Decorators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
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
                .withFallback(throwable -> fallback.get())
                .decorate();

        return decoratedSupplier.get();
    }
}
