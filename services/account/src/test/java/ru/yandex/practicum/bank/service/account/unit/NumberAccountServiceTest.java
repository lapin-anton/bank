package ru.yandex.practicum.bank.service.account.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.service.account.repostory.AccountRepository;
import ru.yandex.practicum.bank.service.account.service.NumberAccountService;
import ru.yandex.practicum.bank.service.account.service.impl.NumberAccountServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = NumberAccountServiceTest.Config.class)
class NumberAccountServiceTest {

    @Configuration
    static class Config {
        @Bean AccountRepository accountRepository() { return mock(AccountRepository.class); }
        @Bean NumberAccountService numberAccountService(AccountRepository accountRepository) {
            return new NumberAccountServiceImpl(accountRepository);
        }
    }

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private NumberAccountService numberAccountService;

    @BeforeEach
    void setup() {
        reset(accountRepository);
    }

    @Test
    void generateNumber_ShouldReturnCorrectFormat() {
        when(accountRepository.count()).thenReturn(42L);

        String number = numberAccountService.generateNumber();

        String expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        assertTrue(number.matches("ACC-" + expectedDate + "-00043"), "Unexpected format: " + number);

        verify(accountRepository).count();
    }
}
