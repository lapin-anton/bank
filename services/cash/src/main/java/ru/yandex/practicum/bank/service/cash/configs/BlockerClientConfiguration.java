package ru.yandex.practicum.bank.service.cash.configs;

import feign.Feign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.bank.client.account.api.AccountApi;
import ru.yandex.practicum.bank.client.blocker.api.BlockerApi;

@Configuration
public class BlockerClientConfiguration {

    @Bean
    public BlockerApi blockerApiClient(Feign.Builder feignBuilder,
            FeignSecurityConfig authConfig,
            @Value("${api.blocker}") String baseUrl) {

        return feignBuilder
                .requestInterceptor(authConfig.jwtRelayInterceptor())
                .logger(new feign.slf4j.Slf4jLogger(BlockerApi.class))
                .target(BlockerApi.class, baseUrl);
    }
}
