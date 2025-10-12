package ru.yandex.practicum.bank.ui.service.impl;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.ui.config.KeycloakAdminProperty;
import ru.yandex.practicum.bank.ui.dto.PasswordUserFormDto;
import ru.yandex.practicum.bank.ui.dto.UserFormDto;
import ru.yandex.practicum.bank.ui.dto.UserItemDto;
import ru.yandex.practicum.bank.ui.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;

    private final KeycloakAdminProperty keycloakAdminProperty;

    @Override
    public void edit(User user, UserFormDto userFormDto) {
        UserResource userResource = keycloak.realm(keycloakAdminProperty.getRealm())
                .users()
                .get(user.getId());

        UserRepresentation userRepresentation = userResource.toRepresentation();

        userRepresentation.setFirstName(userFormDto.getName());
        userRepresentation.setLastName(userFormDto.getFamilyName());
        userRepresentation.setEmail(userFormDto.getEmail());

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("birthDate", List.of(userFormDto.getBirthDate().toString()));

        userRepresentation.setAttributes(attributes);
        userResource.update(userRepresentation);
    }

    @Override
    public void password(User user, PasswordUserFormDto passwordUserFormDto) {
        UserResource userResource = keycloak.realm(keycloakAdminProperty.getRealm())
                .users()
                .get(user.getId());

        CredentialRepresentation newPassword = new CredentialRepresentation();
        newPassword.setType(CredentialRepresentation.PASSWORD);
        newPassword.setValue(passwordUserFormDto.getPassword());
        newPassword.setTemporary(false);

        userResource.resetPassword(newPassword);
    }

    @Override
    public List<UserItemDto> getAllOverUsers(User user) {
        int first = 0;
        int max = 100;
        List<UserRepresentation> allUsers = new java.util.ArrayList<>();

        while (true) {
            List<UserRepresentation> users = keycloak.realm(keycloakAdminProperty.getRealm())
                    .users()
                    .list(first, max);

            if (users.isEmpty()) {
                break;
            }

            allUsers.addAll(users);
            first += max;
        }

        return allUsers.stream()
                .map(UserItemDto::of)
                .filter(item -> !item.getId().equals(user.getId()))
                .toList();
    }
}
