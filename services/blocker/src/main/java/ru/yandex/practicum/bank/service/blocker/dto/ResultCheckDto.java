package ru.yandex.practicum.bank.service.blocker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultCheckDto {

    @NotNull
    private Boolean result;
}
