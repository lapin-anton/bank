package ru.yandex.practicum.bank.service.cash.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.cash.CashServicePracticumBankApplication;
import ru.yandex.practicum.bank.service.cash.dto.CashTransactionDto;
import ru.yandex.practicum.bank.service.cash.service.CashService;
import ru.yandex.practicum.bank.service.cash.service.NotificationService;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {CashServicePracticumBankApplication.class, BaseContractTest.TestContractConfiguration.class})
@ActiveProfiles("test")
public abstract class BaseContractTest {

    @Autowired
    protected WebApplicationContext context;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);

        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "none")
                .claim("sub", "user-456")
                .claim("preferred_username", "testuser")
                .claim("email", "test@example.com")
                .claim("given_name", "Test")
                .claim("family_name", "User")
                .claim("birth_date", "1990-01-01")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(jwt, jwt.getTokenValue(), List.of());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @TestConfiguration
    static class TestContractConfiguration {

        @Bean
        @Primary
        public CashService cashService() {
            CashService mock = mock(CashService.class);

            doNothing().when(mock).putCash(any(CashTransactionDto.class), any(User.class));
            doNothing().when(mock).withdrawCash(any(CashTransactionDto.class), any(User.class));

            return mock;
        }

        @Bean
        @Primary
        public NotificationService notificationService() {
            NotificationService mock = mock(NotificationService.class);

            doNothing().when(mock).notifyPutCash(any(CashTransactionDto.class), any(User.class));
            doNothing().when(mock).notifyWithdrawCash(any(CashTransactionDto.class), any(User.class));

            return mock;
        }
    }
}
