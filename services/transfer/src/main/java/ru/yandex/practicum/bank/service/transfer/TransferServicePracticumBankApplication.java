package ru.yandex.practicum.bank.service.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.yandex.practicum.bank")
public class TransferServicePracticumBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferServicePracticumBankApplication.class, args);
    }
}
