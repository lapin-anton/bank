package ru.yandex.practicum.bank.service.notification.unit;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.bank.service.notification.dto.MailDto;
import ru.yandex.practicum.bank.service.notification.service.MailService;
import ru.yandex.practicum.bank.service.notification.service.impl.MailServiceImpl;

import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = MailServiceTest.Config.class)
@TestPropertySource(properties = "spring.mail.username=noreply@test.com")
class MailServiceTest {

    @Configuration
    static class Config {
        @Bean JavaMailSender javaMailSender() { return mock(JavaMailSender.class); }

        @Bean MailService mailService(JavaMailSender javaMailSender) {
            return new MailServiceImpl(javaMailSender);
        }
    }

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailService mailService;

    @BeforeEach
    void setup() {
        reset(javaMailSender);
    }

    @Test
    void send_ShouldSendMailWithCorrectFields() {
        MailDto mailDto = new MailDto();
        mailDto.setEmail("test@domain.com");
        mailDto.setSubject("Hello");
        mailDto.setText("Test message");

        mailService.send(mailDto);

        verify(javaMailSender).send((SimpleMailMessage) argThat(message -> {
            if (!(message instanceof SimpleMailMessage msg)) return false;
            return msg.getTo() != null &&
                    msg.getTo()[0].equals("test@domain.com") &&
                    msg.getSubject().equals("Hello") &&
                    msg.getText().equals("Test message");
        }));

    }
}
