package ru.yandex.practicum.bank.common.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Resilience4jConfig {

    private static final String CONFIG_NAME = "myFeignBreaker";

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults();
    }

    @Bean
    public RetryRegistry retryRegistry() {
        return RetryRegistry.ofDefaults();
    }

    @Bean
    public TimeLimiterRegistry timeLimiterRegistry() {
        return TimeLimiterRegistry.ofDefaults();
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        return RateLimiterRegistry.ofDefaults();
    }

    @Bean()
    public CircuitBreaker myFeignCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker(CONFIG_NAME);
    }

    @Bean()
    public Retry myFeignRetry(RetryRegistry registry) {
        return registry.retry(CONFIG_NAME);
    }

    @Bean()
    public TimeLimiter myFeignTimeLimiter(TimeLimiterRegistry registry) {
        return registry.timeLimiter(CONFIG_NAME);
    }

    @Bean()
    public RateLimiter myFeignRateLimiter(RateLimiterRegistry registry) {
        return registry.rateLimiter(CONFIG_NAME);
    }
}
