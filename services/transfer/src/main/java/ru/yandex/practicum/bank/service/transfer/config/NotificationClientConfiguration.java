package ru.yandex.practicum.bank.service.transfer.config;

import feign.Feign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.bank.client.notification.api.NotificationApi;

@Configuration
public class NotificationClientConfiguration {

    @Bean
    public NotificationApi notificationApiClient(Feign.Builder feignBuilder,
                                                 FeignSecurityConfig authConfig,
                                                 @Value("${api.notification}") String baseUrl) {

        return feignBuilder
                .requestInterceptor(authConfig.jwtRelayInterceptor())
                .logger(new feign.slf4j.Slf4jLogger(NotificationApi.class))
                .target(NotificationApi.class, baseUrl);
    }
}
