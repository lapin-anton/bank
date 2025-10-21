package ru.yandex.practicum.bank.common.massage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private UUID id;

    private String email;

    private String subject;

    private String text;
}
