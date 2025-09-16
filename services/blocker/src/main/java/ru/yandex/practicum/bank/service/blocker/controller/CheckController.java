package ru.yandex.practicum.bank.service.blocker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.bank.service.blocker.dto.CashCheckDto;
import ru.yandex.practicum.bank.service.blocker.dto.ResultCheckDto;
import ru.yandex.practicum.bank.service.blocker.dto.TransferCheckDto;
import ru.yandex.practicum.bank.service.blocker.service.CheckService;

@RestController
@RequiredArgsConstructor
public class CheckController {

    private final CheckService checkService;

    @PostMapping("/cash")
    public ResponseEntity<ResultCheckDto> checkCash(@Valid @RequestBody CashCheckDto cashCheckDto) {

        return ResponseEntity.ok(checkService.check(cashCheckDto));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ResultCheckDto> checkTransfer(@Valid @RequestBody TransferCheckDto transferCheckDto) {

        return ResponseEntity.ok(checkService.check(transferCheckDto));
    }
}
