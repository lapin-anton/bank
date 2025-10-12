package ru.yandex.practicum.bank.ui.config;

import feign.Feign;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.bank.client.cash.api.CashApi;

@Configuration
public class CashClientConfiguration {

    @Bean
    public CashApi cashApiClient(Feign.Builder feignBuilder,
                                 FeignSecurityConfig authConfig,
                                 @Value("${api.cash}") String baseUrl) {

        return feignBuilder
                .requestInterceptor(authConfig.userTokenRelayInterceptor())
                .logger(new Slf4jLogger(CashApi.class))
                .target(CashApi.class, baseUrl);
    }
}
