package ru.yandex.practicum.bank.service.notification.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.bank.service.notification.config.JwtTestConfig;
import ru.yandex.practicum.bank.service.notification.config.MockMailConfig;
import ru.yandex.practicum.bank.service.notification.dto.MailDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({JwtTestConfig.class, MockMailConfig.class})
class MailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JavaMailSender mailSender;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer mock.jwt.token";

    @Test
    void sendMail_ShouldReturnOk() throws Exception {
        MailDto mailDto = new MailDto();
        mailDto.setEmail("test@example.com");
        mailDto.setSubject("Subject");
        mailDto.setText("Hello from test");

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        mockMvc.perform(post("/mail")
                        .header(AUTH_HEADER, BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mailDto)))
                .andExpect(status().isOk());

        Mockito.verify(mailSender, Mockito.times(1)).send(any(SimpleMailMessage.class));
    }
}
