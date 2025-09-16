package ru.yandex.practicum.bank.service.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "ru.yandex.practicum.bank")
@EntityScan(basePackages = "ru.yandex.practicum.bank")
@SpringBootApplication(scanBasePackages = "ru.yandex.practicum.bank")
public class AccountServicePracticumBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServicePracticumBankApplication.class, args);
    }
}
