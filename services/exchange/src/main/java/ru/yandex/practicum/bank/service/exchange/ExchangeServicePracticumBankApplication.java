package ru.yandex.practicum.bank.service.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "ru.yandex.practicum.bank")
@EntityScan(basePackages = "ru.yandex.practicum.bank")
@SpringBootApplication(scanBasePackages = "ru.yandex.practicum.bank")
public class ExchangeServicePracticumBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeServicePracticumBankApplication.class, args);
    }
}
