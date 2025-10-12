package ru.yandex.practicum.bank.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public String handleValidationException(Throwable ex, Model model) {
        log.error(ex.getMessage(), ex);
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
