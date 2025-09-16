package ru.yandex.practicum.bank.common.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private String id;

    private String login;

    private String email;

    private String name;

    private String familyName;

    private LocalDate birthDate;
}
