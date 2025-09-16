package ru.yandex.practicum.bank.ui.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KeycloakAdminProperty.class)
@RequiredArgsConstructor
public class KeycloakAdminConfig {

    private final KeycloakAdminProperty keycloakAdminProperty;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakAdminProperty.getServerUrl())
                .realm("master")
                .clientId("admin-cli")
                .grantType(OAuth2Constants.PASSWORD)
                .username(keycloakAdminProperty.getUsername())
                .password(keycloakAdminProperty.getPassword())
                .build();
    }
}
