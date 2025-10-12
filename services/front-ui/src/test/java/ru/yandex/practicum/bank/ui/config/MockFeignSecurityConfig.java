package ru.yandex.practicum.bank.ui.config;

import feign.RequestInterceptor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockFeignSecurityConfig {

    @Bean(name = "mockUserTokenRelayInterceptor")
    @Primary
    public RequestInterceptor userTokenRelayInterceptor() {
        return template -> {};
    }
}
