package ru.yandex.practicum.bank.service.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.bank.service.notification.dto.MailDto;
import ru.yandex.practicum.bank.service.notification.service.MailService;

@RequiredArgsConstructor
@RestController
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail")
    public ResponseEntity<Void> sendMail(@RequestBody MailDto mailDto) {
        mailService.send(mailDto);

        return ResponseEntity.ok().build();
    }
}
