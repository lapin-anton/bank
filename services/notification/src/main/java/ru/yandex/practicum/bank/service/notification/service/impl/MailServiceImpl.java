package ru.yandex.practicum.bank.service.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.service.notification.dto.MailDto;
import ru.yandex.practicum.bank.service.notification.service.MailService;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void send(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getEmail());
        message.setSubject(mailDto.getSubject());
        message.setText(mailDto.getText());
        message.setFrom(from);
        mailSender.send(message);
    }
}
