package ru.yandex.practicum.bank.service.transfer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.bank.common.annotation.CurrentUser;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.transfer.dto.TransferDto;
import ru.yandex.practicum.bank.service.transfer.service.TransferService;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/")
    ResponseEntity<Void> transfer(@Valid @RequestBody TransferDto transferDto, @CurrentUser User user) {

        transferService.transfer(transferDto, user);

        return ResponseEntity.ok().build();
    }
}
