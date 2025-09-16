package ru.yandex.practicum.bank.ui.dto;

import lombok.Data;
import org.keycloak.representations.idm.UserRepresentation;

@Data
public class UserItemDto {

    private String id;

    private String login;

    public static UserItemDto of(UserRepresentation userRepresentation) {
        UserItemDto userItemDto = new UserItemDto();

        userItemDto.setId(userRepresentation.getId());
        userItemDto.setLogin(userRepresentation.getUsername());

        return userItemDto;
    }
}
