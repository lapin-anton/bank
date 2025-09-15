package ru.yandex.practicum.bank.service.cash.configs;

import feign.Feign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.bank.client.account.api.AccountApi;

@Configuration
public class AccountClientConfiguration {

    @Bean
    public AccountApi accountApiClient(Feign.Builder feignBuilder,
            FeignSecurityConfig authConfig,
            @Value("${api.account}") String baseUrl) {

        return feignBuilder
                .requestInterceptor(authConfig.jwtRelayInterceptor())
                .logger(new feign.slf4j.Slf4jLogger(AccountApi.class))
                .target(AccountApi.class, baseUrl);
    }
}
