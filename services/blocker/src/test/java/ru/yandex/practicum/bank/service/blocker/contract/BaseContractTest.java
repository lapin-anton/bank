package ru.yandex.practicum.bank.service.blocker.contract;

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
import ru.yandex.practicum.bank.service.blocker.BlockerServicePracticumBankApplication;
import ru.yandex.practicum.bank.service.blocker.dto.*;
import ru.yandex.practicum.bank.service.blocker.service.CheckService;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BlockerServicePracticumBankApplication.class, BaseContractTest.TestContractConfiguration.class})
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
        public CheckService checkService() {
            CheckService mock = mock(CheckService.class);

            when(mock.check(any(CashCheckDto.class)))
                    .thenReturn(new ResultCheckDto(true));

            when(mock.check(any(TransferCheckDto.class)))
                    .thenReturn(new ResultCheckDto(true));

            return mock;
        }
    }
}
