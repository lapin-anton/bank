package ru.yandex.practicum.bank.ui.config;

import feign.Feign;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.bank.client.transfer.api.TransferApi;

@Configuration
public class TransferClientConfiguration {

    @Bean
    public TransferApi transferApiClient(Feign.Builder feignBuilder,
                                     FeignSecurityConfig authConfig,
                                     @Value("${api.transfer}") String baseUrl) {

        return feignBuilder
                .requestInterceptor(authConfig.userTokenRelayInterceptor())
                .logger(new Slf4jLogger(TransferApi.class))
                .target(TransferApi.class, baseUrl);
    }
}
