package ru.yandex.practicum.bank.service.cash.contract;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import ru.yandex.practicum.bank.client.notification.api.NotificationClient;
import ru.yandex.practicum.bank.client.notification.model.MailDto;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum.bank:notification-stubs:+:stubs:8080",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class NotificationContractTest {

    @Autowired
    private NotificationClient notificationClient;

    @Test
    void shouldSendMailAccordingToContract() {
        MailDto dto = new MailDto();
        dto.setEmail("user@example.com");
        dto.setSubject("Уведомление о платеже");
        dto.setText("Ваш перевод успешно выполнен.");

        assertDoesNotThrow(() -> notificationClient.sendMail(dto));
    }
}

