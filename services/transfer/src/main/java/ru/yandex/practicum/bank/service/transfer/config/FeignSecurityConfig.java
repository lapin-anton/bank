package ru.yandex.practicum.bank.service.transfer.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FeignSecurityConfig {

    @Bean
    public RequestInterceptor jwtRelayInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String jwt = extractJwtFromContext();
                if (jwt != null) {
                    template.header("Authorization", "Bearer " + jwt);
                    log.info("Attached JWT to Feign request");
                } else {
                    log.warn("No JWT token found to relay in Feign request");
                }
            }

            private String extractJwtFromContext() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                    return jwtAuth.getToken().getTokenValue();
                }

                log.warn("Authentication is not of type JwtAuthenticationToken: {}", authentication.getClass().getSimpleName());
                return null;
            }
        };
    }
}
