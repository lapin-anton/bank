package ru.yandex.practicum.bank.ui.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Collections;

import static org.mockito.Mockito.*;

@TestConfiguration
public class MockKeycloakConfig {

    // @Bean
    // @Primary
    // public KeycloakAdminProperty keycloakAdminProperty() {
    // KeycloakAdminProperty property = new KeycloakAdminProperty();
    // property.setRealm("bank");
    // property.setServerUrl("http://localhost:8480");
    // return property;
    // }

    @Bean
    @Primary
    public Keycloak keycloak() {
        Keycloak mockKeycloak = mock(Keycloak.class);
        RealmResource realmResource = mock(RealmResource.class);
        UsersResource usersResource = mock(UsersResource.class);
        UserResource userResource = mock(UserResource.class);

        when(mockKeycloak.realm(anyString())).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);

        when(usersResource.get(anyString())).thenReturn(userResource);
        when(userResource.toRepresentation()).thenReturn(new UserRepresentation());

        when(usersResource.list(anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        return mockKeycloak;
    }
}
