package ru.yandex.practicum.bank.service.cash.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.bank.common.annotation.CurrentUser;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.cash.dto.CashTransactionDto;
import ru.yandex.practicum.bank.service.cash.service.CashService;

@RestController
@RequiredArgsConstructor
public class CashController {

    private final CashService cashService;

    @PutMapping("/put")
    public ResponseEntity<Void> putCash(@Valid @RequestBody CashTransactionDto transactionDto, @CurrentUser User user) {
        cashService.putCash(transactionDto, user);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/withdraw")
    public ResponseEntity<Void> withdrawCash(@Valid @RequestBody CashTransactionDto transactionDto, @CurrentUser User user) {
        cashService.withdrawCash(transactionDto, user);

        return ResponseEntity.ok().build();
    }
}
