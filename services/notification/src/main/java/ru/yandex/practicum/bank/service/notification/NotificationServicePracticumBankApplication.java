package ru.yandex.practicum.bank.service.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.yandex.practicum.bank"})
public class NotificationServicePracticumBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServicePracticumBankApplication.class, args);
    }

}
