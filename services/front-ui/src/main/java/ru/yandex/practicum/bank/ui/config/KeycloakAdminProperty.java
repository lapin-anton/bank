package ru.yandex.practicum.bank.ui.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "keycloak-admin")
public class KeycloakAdminProperty {

    private String serverUrl;

    private String username;

    private String password;

    private String realm;
}
