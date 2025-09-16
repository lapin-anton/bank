package ru.yandex.practicum.bank.ui.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.bank.ui.annotation.ValidAge;
import ru.yandex.practicum.bank.common.model.User;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserFormDto {

    @NotBlank(message = "Поле Имя не может быть пустым")
    private String name;

    @NotBlank(message = "Поле Фамилия не может быть пустым")
    private String familyName;

    @NotBlank(message = "Поле Email не может быть пустым")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ValidAge
    private LocalDate birthDate;

    public static UserFormDto of(User user) {
        UserFormDto userFormDto = new UserFormDto();

        userFormDto.setName(user.getName());
        userFormDto.setFamilyName(user.getFamilyName());
        userFormDto.setEmail(user.getEmail());
        userFormDto.setBirthDate(user.getBirthDate());

        return userFormDto;
    }
}
