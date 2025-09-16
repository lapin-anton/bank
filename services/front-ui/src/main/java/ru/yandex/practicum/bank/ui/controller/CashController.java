package ru.yandex.practicum.bank.ui.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.bank.client.cash.api.CashClient;
import ru.yandex.practicum.bank.client.cash.model.CashTransactionDto;

@Controller
@RequestMapping("/cash")
@RequiredArgsConstructor
public class CashController {

    private final CashClient cashClient;

    @PostMapping("/put")
    public String putCash(@Valid @ModelAttribute("cashTransaction") CashTransactionDto cashTransactionDto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formErrors", result.getAllErrors());

            return "error";
        }

        cashClient.putCash(cashTransactionDto);

        return "redirect:/";
    }

    @PostMapping("/withdraw")
    public String withdrawCash(@Valid @ModelAttribute("cashTransaction") CashTransactionDto cashTransactionDto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formErrors", result.getAllErrors());

            return "error";
        }

        cashClient.withdrawCash(cashTransactionDto);

        return "redirect:/";
    }
}
