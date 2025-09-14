package ru.yandex.practicum.bank.service.account.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.common.exception.BadRequestException;
import ru.yandex.practicum.bank.common.exception.NotFoundException;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.account.dto.AccountDto;
import ru.yandex.practicum.bank.service.account.mapper.AccountMapper;
import ru.yandex.practicum.bank.service.account.model.*;
import ru.yandex.practicum.bank.service.account.repostory.AccountRepository;
import ru.yandex.practicum.bank.service.account.service.AccountService;
import ru.yandex.practicum.bank.service.account.service.NumberAccountService;
import ru.yandex.practicum.bank.service.account.service.impl.AccountServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = AccountServiceTest.Config.class)
class AccountServiceTest {

    private final User user = new User();
    private Account account;

    @Configuration
    static class Config {
        @Bean
        AccountRepository accountRepository() {
            return mock(AccountRepository.class);
        }

        @Bean
        AccountMapper accountMapper() {
            return mock(AccountMapper.class);
        }

        @Bean
        NumberAccountService numberAccountService() {
            return mock(NumberAccountService.class);
        }

        @Bean
        AccountService accountService(AccountRepository accountRepository, AccountMapper accountMapper,
                NumberAccountService numberAccountService) {
            return new AccountServiceImpl(accountRepository, accountMapper, numberAccountService);
        }
    }

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private NumberAccountService numberAccountService;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void setup() {
        user.setId("user123");
        account = new Account();
        account.setTestId(1L);
        account.setUserId("user123");
        account.setCurrency(Currency.RUB);
        account.setNumber("1234567890");
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);
        account.setVersion(1L);
        reset(accountRepository, accountMapper, numberAccountService);
    }

    @Test
    void getAccounts_ShouldReturnList() {
        when(accountRepository.getAllByUserId("user123")).thenReturn(List.of(account));
        when(accountMapper.toDto(account)).thenReturn(new AccountDto());

        List<AccountDto> result = accountService.getAccounts("user123");

        assertEquals(1, result.size());
        verify(accountRepository).getAllByUserId("user123");
        verify(accountMapper).toDto(account);
    }

    @Test
    void getAccount_ShouldReturnDto() {
        when(accountRepository.getByNumber("1234567890")).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(new AccountDto());

        AccountDto dto = accountService.getAccount("1234567890");

        assertNotNull(dto);
        verify(accountRepository).getByNumber("1234567890");
        verify(accountMapper).toDto(account);
    }

    @Test
    void getAccount_ShouldThrow_WhenNotFound() {
        when(accountRepository.getByNumber("unknown")).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> accountService.getAccount("unknown"));
        assertTrue(ex.getMessage().contains("Account not found"));
    }

    @Test
    void openAccount_ShouldReturnDto() {
        when(accountRepository.existsByCurrencyAndUserId(Currency.RUB, "user123")).thenReturn(false);
        when(numberAccountService.generateNumber()).thenReturn("generated123");
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));
        when(accountMapper.toDto(any(Account.class))).thenReturn(new AccountDto());

        AccountDto dto = accountService.openAccount(user, Currency.RUB);

        assertNotNull(dto);
        verify(accountRepository).existsByCurrencyAndUserId(Currency.RUB, "user123");
        verify(numberAccountService).generateNumber();
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void openAccount_ShouldThrow_WhenCurrencyExists() {
        when(accountRepository.existsByCurrencyAndUserId(Currency.RUB, "user123")).thenReturn(true);

        assertThrows(BadRequestException.class, () -> accountService.openAccount(user, Currency.RUB));
        verify(accountRepository, never()).save(any());
    }

    @Test
    void deleteAccount_ShouldDelete_WhenValid() {
        account.setBalance(BigDecimal.ZERO);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.deleteAccount(user, 1L);

        verify(accountRepository).delete(account);
    }

    @Test
    void deleteAccount_ShouldThrow_WhenUserMismatch() {
        account.setUserId("otherUser");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> accountService.deleteAccount(user, 1L));
        verify(accountRepository, never()).delete(any());
    }

    @Test
    void deleteAccount_ShouldThrow_WhenBalanceNotZero() {
        account.setBalance(BigDecimal.valueOf(100));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> accountService.deleteAccount(user, 1L));
    }

    @Test
    void blockAccount_ShouldUpdateStatus() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.blockAccount(1L);

        assertEquals(AccountStatus.BLOCKED, account.getStatus());
        verify(accountRepository).save(account);
    }

    @Test
    void changeBalance_ShouldUpdate_WhenValid() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.changeBalance(1L, BigDecimal.TEN, 1L);

        assertEquals(BigDecimal.TEN, account.getBalance());
        verify(accountRepository).save(account);
    }

    @Test
    void changeBalance_ShouldThrow_WhenBlocked() {
        account.setStatus(AccountStatus.BLOCKED);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> accountService.changeBalance(1L, BigDecimal.ONE, 1L));
    }

    @Test
    void changeBalance_ShouldThrow_WhenVersionMismatch() {
        account.setVersion(2L);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> accountService.changeBalance(1L, BigDecimal.ONE, 1L));
    }
}
