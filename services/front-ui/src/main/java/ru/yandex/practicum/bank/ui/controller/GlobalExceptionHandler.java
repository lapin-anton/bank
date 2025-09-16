package ru.yandex.practicum.bank.ui.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public String handleValidationException(Throwable ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
