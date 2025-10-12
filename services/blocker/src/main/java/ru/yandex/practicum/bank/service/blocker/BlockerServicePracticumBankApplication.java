package ru.yandex.practicum.bank.service.blocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.yandex.practicum.bank"})
public class BlockerServicePracticumBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockerServicePracticumBankApplication.class, args);
    }
}
