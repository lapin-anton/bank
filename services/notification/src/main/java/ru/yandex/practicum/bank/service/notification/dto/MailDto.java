package ru.yandex.practicum.bank.service.notification.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MailDto {

    @NotNull
    private String email;

    @NotNull
    private String subject;

    @NotNull
    private String text;
}
