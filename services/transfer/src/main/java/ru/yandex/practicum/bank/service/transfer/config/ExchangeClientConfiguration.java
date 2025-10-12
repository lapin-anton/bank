package ru.yandex.practicum.bank.service.transfer.config;

import feign.Feign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.bank.client.exchange.api.ExchangeApi;

@Configuration
public class ExchangeClientConfiguration {

    @Bean
    public ExchangeApi exchangeApiClient(Feign.Builder feignBuilder,
                                       FeignSecurityConfig authConfig,
                                       @Value("${api.exchange}") String baseUrl) {

        return feignBuilder
                .requestInterceptor(authConfig.jwtRelayInterceptor())
                .logger(new feign.slf4j.Slf4jLogger(ExchangeApi.class))
                .target(ExchangeApi.class, baseUrl);
    }
}
