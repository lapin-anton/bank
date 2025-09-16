package ru.yandex.practicum.bank.service.notification.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.bank.service.notification.NotificationServicePracticumBankApplication;

@SpringBootTest(classes = NotificationServicePracticumBankApplication.class)
@ActiveProfiles("test")
public abstract class BaseContractTest {

    @Autowired
    protected WebApplicationContext context;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }
}
