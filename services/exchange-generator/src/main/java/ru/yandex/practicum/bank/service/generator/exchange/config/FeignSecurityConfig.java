package ru.yandex.practicum.bank.service.generator.exchange.config;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FeignSecurityConfig {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    private static final String CLIENT_REGISTRATION_ID = "exchange-generator-client";

    @Bean
    public RequestInterceptor clientCredentialsInterceptor() {
        return template -> {
            OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                    .withClientRegistrationId(CLIENT_REGISTRATION_ID)
                    .principal("system")
                    .build();

            OAuth2AuthorizedClient client = authorizedClientManager.authorize(request);

            if (client != null && client.getAccessToken() != null) {
                String tokenValue = client.getAccessToken().getTokenValue();
                template.header("Authorization", "Bearer " + tokenValue);
                log.info("Attached client credentials token to Feign request");
            } else {
                log.error("Failed to retrieve client credentials access token for Feign");
            }
        };
    }
}
