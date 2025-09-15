package ru.yandex.practicum.bank.service.cash.contract;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import ru.yandex.practicum.bank.client.account.api.AccountClient;
import ru.yandex.practicum.bank.client.account.model.AccountDto;
import ru.yandex.practicum.bank.client.account.model.ChangeBalanceDto;
import ru.yandex.practicum.bank.client.account.model.Currency;
import ru.yandex.practicum.bank.client.account.model.OpenAccountDto;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(ids = "ru.yandex.practicum.bank:account-stubs:+:stubs:8080", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class AccountApiContractTest {

    @Autowired
    private AccountClient accountClient;

    @Test
    void addAccount_returnsNonNullResponse() {
        OpenAccountDto dto = new OpenAccountDto();
        dto.setCurrency(Currency.RUB);

        AccountDto response = assertDoesNotThrow(() -> accountClient.addAccount(dto));
        assertNotNull(response);
    }

    @Test
    void blockAccount_executesWithoutException() {
        assertDoesNotThrow(() -> accountClient.blockAccount(1L));
    }

    @Test
    void changeBalance_executesWithoutException() {
        ChangeBalanceDto dto = new ChangeBalanceDto();
        dto.setAmount(new BigDecimal("100.00"));

        assertDoesNotThrow(() -> accountClient.changeBalance(1L, dto));
    }

    @Test
    void deleteAccount_executesWithoutException() {
        assertDoesNotThrow(() -> accountClient.deleteAccount(42L));
    }

    @Test
    void getAccountByNumber_returnsExpectedAccount() {
        AccountDto account = assertDoesNotThrow(() -> accountClient.getAccountByNumber("40817810099910004321"));
        assertNotNull(account);
    }

    @Test
    void getAccountsByUserId_returnsAccounts() {
        List<AccountDto> accounts = assertDoesNotThrow(() -> accountClient.getAccountsByUserId("user-456"));
        assertNotNull(accounts);
    }
}
