package ru.yandex.practicum.bank.ui.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FeignSecurityConfig {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Bean
    public RequestInterceptor userTokenRelayInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String token = extractToken();
                if (token != null) {
                    template.header("Authorization", "Bearer " + token);
                    log.info("Attached Bearer token to Feign request");
                } else {
                    log.warn("No Bearer token available for Feign request.");
                }
            }

            private String extractToken() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (!(authentication instanceof OAuth2AuthenticationToken oauth2Token)) {
                    log.warn("Current auth is not OAuth2AuthenticationToken: {}", authentication);
                    return null;
                }

                OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                        .withClientRegistrationId(oauth2Token.getAuthorizedClientRegistrationId())
                        .principal(oauth2Token)
                        .build();

                OAuth2AuthorizedClient client = authorizedClientManager.authorize(authorizeRequest);
                if (client != null && client.getAccessToken() != null) {
                    return client.getAccessToken().getTokenValue();
                } else {
                    log.warn("Failed to retrieve OAuth2AuthorizedClient or token");
                }
                return null;
            }
        };
    }
}
