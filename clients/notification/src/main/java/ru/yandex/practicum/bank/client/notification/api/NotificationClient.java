package ru.yandex.practicum.bank.client.notification.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.bank.client.notification.model.MailDto;
import ru.yandex.practicum.bank.common.resilience.ResilienceExecutor;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationClient {

    private final NotificationApi notificationApi;

    private final ResilienceExecutor resilience;

    public void sendMail(MailDto mailDto) {
        resilience.execute(
                () -> {
                    notificationApi.sendMail(mailDto);
                    return null;
                },
                () -> {
                    log.warn("Fallback: failed to send mail to {}", mailDto.getEmail());
                    return null;
                }
        );
    }
}
