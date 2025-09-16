package ru.yandex.practicum.bank.service.generator.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableScheduling
@SpringBootApplication(scanBasePackages = "ru.yandex.practicum.bank")
public class ExchangeGeneratorServicePracticumBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeGeneratorServicePracticumBankApplication.class, args);
    }

}
