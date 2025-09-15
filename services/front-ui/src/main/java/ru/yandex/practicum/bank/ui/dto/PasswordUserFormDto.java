package ru.yandex.practicum.bank.ui.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.yandex.practicum.bank.ui.annotation.PasswordMatches;

@Data
@PasswordMatches
public class PasswordUserFormDto {

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;
}
