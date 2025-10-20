package ru.yandex.practicum.bank.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.yandex.practicum.bank"})
public class FrontUiPracticumBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontUiPracticumBankApplication.class, args);
    }
}
