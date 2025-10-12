package ru.yandex.practicum.bank.ui.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.ui.config.KeycloakAdminProperty;
import ru.yandex.practicum.bank.ui.dto.PasswordUserFormDto;
import ru.yandex.practicum.bank.ui.dto.UserFormDto;
import ru.yandex.practicum.bank.ui.dto.UserItemDto;
import ru.yandex.practicum.bank.ui.service.UserService;
import ru.yandex.practicum.bank.ui.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = UserServiceTest.Config.class)
class UserServiceTest {

    @Configuration
    static class Config {
        @Bean Keycloak keycloak() { return mock(Keycloak.class); }
        @Bean KeycloakAdminProperty keycloakAdminProperty() {
            KeycloakAdminProperty prop = new KeycloakAdminProperty();
            prop.setRealm("test-realm");
            return prop;
        }
        @Bean UserService userService(Keycloak keycloak, KeycloakAdminProperty keycloakAdminProperty) {
            return new UserServiceImpl(keycloak, keycloakAdminProperty);
        }
    }

    @Autowired
    private Keycloak keycloak;

    @Autowired
    private KeycloakAdminProperty keycloakAdminProperty;

    @Autowired
    private UserService userService;

    private RealmResource realmResource;
    private UsersResource usersResource;
    private UserResource userResource;
    private User user;

    @BeforeEach
    void setup() {
        reset(keycloak);
        realmResource = mock(RealmResource.class);
        usersResource = mock(UsersResource.class);
        userResource = mock(UserResource.class);

        user = new User();
        user.setId("user123");

        when(keycloak.realm(keycloakAdminProperty.getRealm())).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.get("user123")).thenReturn(userResource);
    }

    @Test
    void edit_ShouldUpdateUserRepresentation() {
        UserFormDto dto = new UserFormDto();
        dto.setName("Ivan");
        dto.setFamilyName("Petrov");
        dto.setEmail("ivan@example.com");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));

        UserRepresentation representation = new UserRepresentation();
        when(userResource.toRepresentation()).thenReturn(representation);

        userService.edit(user, dto);

        assertEquals("Ivan", representation.getFirstName());
        assertEquals("Petrov", representation.getLastName());
        assertEquals("ivan@example.com", representation.getEmail());
        assertEquals(List.of("1990-01-01"), representation.getAttributes().get("birthDate"));

        verify(userResource).update(representation);
    }

    @Test
    void password_ShouldResetPassword() {
        PasswordUserFormDto dto = new PasswordUserFormDto();
        dto.setPassword("secure123");

        userService.password(user, dto);

        verify(userResource).resetPassword(argThat(cred ->
                cred.getType().equals(CredentialRepresentation.PASSWORD) &&
                        cred.getValue().equals("secure123") &&
                        !cred.isTemporary()
        ));
    }

    @Test
    void getAllOverUsers_ShouldReturnListWithoutCurrentUser() {
        UserRepresentation otherUser = new UserRepresentation();
        otherUser.setId("user999");

        UserRepresentation currentUser = new UserRepresentation();
        currentUser.setId("user123");

        when(usersResource.list(0, 100)).thenReturn(List.of(otherUser, currentUser));
        when(usersResource.list(100, 100)).thenReturn(List.of());

        List<UserItemDto> result = userService.getAllOverUsers(user);

        assertEquals(1, result.size());
        assertEquals("user999", result.getFirst().getId());
    }
}
