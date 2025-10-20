package ru.yandex.practicum.bank.service.account.contract;

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
import ru.yandex.practicum.bank.service.account.AccountServicePracticumBankApplication;
import ru.yandex.practicum.bank.service.account.model.AccountStatus;
import ru.yandex.practicum.bank.service.account.model.Currency;
import ru.yandex.practicum.bank.service.account.service.AccountService;
import ru.yandex.practicum.bank.service.account.service.impl.AccountServiceImpl;
import ru.yandex.practicum.bank.service.account.dto.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {AccountServicePracticumBankApplication.class, BaseContractTest.TestContractConfiguration.class})
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
        public AccountService accountService() {
            AccountService mock = mock(AccountServiceImpl.class);

            AccountDto account1001 = AccountDto.builder()
                    .id(1001L)
                    .number("40817810000000000001")
                    .userId("user-456")
                    .balance(BigDecimal.valueOf(5000.00))
                    .status(AccountStatus.ACTIVE)
                    .currency(Currency.RUB)
                    .version(1L)
                    .build();

            AccountDto account1003 = AccountDto.builder()
                    .id(1003L)
                    .number("40817810000000000002")
                    .userId("user-456")
                    .balance(BigDecimal.valueOf(6000.00))
                    .status(AccountStatus.BLOCKED)
                    .currency(Currency.EUR)
                    .version(2L)
                    .build();

            when(mock.getAccount("40817810099910004321")).thenReturn(
                    AccountDto.builder()
                            .id(2001L)
                            .number("40817810099910004321")
                            .userId("user-456")
                            .balance(BigDecimal.valueOf(7000.00))
                            .status(AccountStatus.ACTIVE)
                            .currency(Currency.EUR)
                            .version(3L)
                            .build()
            );

            when(mock.openAccount(any(User.class), eq(Currency.RUB))).thenReturn(account1001);

            when(mock.getAccounts("user-456")).thenReturn(List.of(account1001));


            when(mock.getAccount("40817810000000000001")).thenReturn(account1001);

            when(mock.getAccount("40817810000000000002")).thenReturn(account1003);

            doNothing().when(mock).deleteAccount(any(User.class), anyLong());


            doNothing().when(mock).blockAccount(anyLong());

            doNothing().when(mock).changeBalance(anyLong(), any(BigDecimal.class), anyLong());

            return mock;
        }

    }
}
