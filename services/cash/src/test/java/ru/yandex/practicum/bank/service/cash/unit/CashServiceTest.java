package ru.yandex.practicum.bank.service.cash.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.client.account.api.AccountClient;
import ru.yandex.practicum.bank.client.account.model.AccountDto;
import ru.yandex.practicum.bank.client.blocker.api.BlockerClient;
import ru.yandex.practicum.bank.client.blocker.model.CashCheckDto;
import ru.yandex.practicum.bank.client.blocker.model.ResultCheckDto;
import ru.yandex.practicum.bank.common.exception.BadRequestException;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.cash.dto.CashTransactionDto;
import ru.yandex.practicum.bank.service.cash.service.CashService;
import ru.yandex.practicum.bank.service.cash.service.impl.CashServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = CashServiceTest.Config.class)
class CashServiceTest {

    @Configuration
    static class Config {
        @Bean AccountClient accountClient() { return mock(AccountClient.class); }
        @Bean BlockerClient blockerClient() { return mock(BlockerClient.class); }
        @Bean CashService cashService(AccountClient accountClient, BlockerClient blockerClient) {
            return new CashServiceImpl(accountClient, blockerClient);
        }
    }

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private BlockerClient blockerClient;

    @Autowired
    private CashService cashService;

    private User user;
    private CashTransactionDto transactionDto;
    private AccountDto accountDto;

    @BeforeEach
    void setup() {
        reset(accountClient, blockerClient);

        user = new User();
        user.setId("user123");

        transactionDto = new CashTransactionDto();
        transactionDto.setAccountNumber("ACC123");
        transactionDto.setAmount(new BigDecimal("500"));

        accountDto = new AccountDto();
        accountDto.setId(1L);
        accountDto.setUserId("user123");
        accountDto.setBalance(new BigDecimal("1000"));
        accountDto.setVersion(1L);

        when(accountClient.getAccountByNumber("ACC123")).thenReturn(accountDto);
        when(blockerClient.checkCash(any(CashCheckDto.class))).thenReturn(new ResultCheckDto(true));
    }

    @Test
    void putCash_ShouldCallChangeBalance() {
        cashService.putCash(transactionDto, user);

        verify(accountClient).changeBalance(eq(1L), argThat(dto ->
                dto.getAmount().equals(new BigDecimal("1500")) &&
                        dto.getVersion().equals(1L)
        ));
    }

    @Test
    void withdrawCash_ShouldCallChangeBalance() {
        cashService.withdrawCash(transactionDto, user);

        verify(accountClient).changeBalance(eq(1L), argThat(dto ->
                dto.getAmount().equals(new BigDecimal("500")) &&
                        dto.getVersion().equals(1L)
        ));
    }

    @Test
    void withdrawCash_ShouldThrow_WhenInsufficientFunds() {
        accountDto.setBalance(new BigDecimal("100"));

        BadRequestException ex = assertThrows(BadRequestException.class, () ->
                cashService.withdrawCash(transactionDto, user)
        );

        assertEquals("Insufficient funds in the account", ex.getMessage());
        verify(accountClient, never()).changeBalance(anyLong(), any());
    }

    @Test
    void shouldThrowAccessDenied_WhenUserNotOwner() {
        accountDto.setUserId("otherUser");

        assertThrows(org.springframework.security.access.AccessDeniedException.class, () ->
                cashService.putCash(transactionDto, user)
        );
    }

    @Test
    void shouldThrowAccessDenied_WhenCheckFails() {
        when(blockerClient.checkCash(any())).thenReturn(new ResultCheckDto(false));

        assertThrows(org.springframework.security.access.AccessDeniedException.class, () ->
                cashService.putCash(transactionDto, user)
        );
    }
}
