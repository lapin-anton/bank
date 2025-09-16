package ru.yandex.practicum.bank.ui.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yandex.practicum.bank.client.transfer.api.TransferClient;
import ru.yandex.practicum.bank.client.transfer.model.TransferDto;

@Controller
@RequiredArgsConstructor
public class TransferController {

    private final TransferClient transferClient;

    @PostMapping("/transfer")
    public String transfer(@Valid @ModelAttribute("transfer") TransferDto transferDto, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formErrors", result.getAllErrors());

            return "error";
        }

        transferClient.transfer(transferDto);

        return "redirect:/";
    }
}
