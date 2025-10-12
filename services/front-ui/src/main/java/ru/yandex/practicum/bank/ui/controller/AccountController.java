package ru.yandex.practicum.bank.ui.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.bank.client.account.api.AccountClient;
import ru.yandex.practicum.bank.client.account.model.AccountDto;
import ru.yandex.practicum.bank.client.account.model.OpenAccountDto;

import java.util.List;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountClient accountClient;

    @PostMapping
    public String addAccount(@Valid @ModelAttribute("account") OpenAccountDto openAccountDto,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formErrors", result.getAllErrors());

            return "error";
        }

        accountClient.addAccount(openAccountDto);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountClient.deleteAccount(id);
        return "redirect:/";
    }

    @GetMapping("/account/user/{userId}")
    public ResponseEntity<List<AccountDto>> getAccounts(@PathVariable String userId) {
        return ResponseEntity.ok(accountClient.getAccountsByUserId(userId));
    }
}
