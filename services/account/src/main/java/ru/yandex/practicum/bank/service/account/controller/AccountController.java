package ru.yandex.practicum.bank.service.account.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bank.service.account.dto.AccountDto;
import ru.yandex.practicum.bank.service.account.dto.ChangeBalanceDto;
import ru.yandex.practicum.bank.service.account.service.AccountService;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{numberAccount}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable String numberAccount) {
        return ResponseEntity.ok(accountService.getAccount(numberAccount));
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<Void> blockAccount(@PathVariable Long id) {
        accountService.blockAccount(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/balance")
    public ResponseEntity<Void> changeBalance(@PathVariable Long id, @RequestBody ChangeBalanceDto changeBalanceDto) {
        accountService.changeBalance(id, changeBalanceDto.getAmount(), changeBalanceDto.getVersion());

        return ResponseEntity.ok().build();
    }
}