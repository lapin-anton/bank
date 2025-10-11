package ru.yandex.practicum.bank.client.account.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.bank.client.account.model.AccountDto;
import ru.yandex.practicum.bank.client.account.model.ChangeBalanceDto;
import ru.yandex.practicum.bank.client.account.model.OpenAccountDto;
import ru.yandex.practicum.bank.common.resilience.ResilienceExecutor;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountClient {

    private final AccountApi accountApi;

    private final ResilienceExecutor resilience;

    public AccountDto addAccount(OpenAccountDto dto) {
        return resilience.execute(
                () -> accountApi.addAccount(dto),
                this::getFallbackAccount
        );
    }

    public void blockAccount(Long id) {
        resilience.execute(
                () -> {
                    accountApi.blockAccount(id);
                    return null;
                },
                () -> null
        );
    }

    public void changeBalance(Long id, ChangeBalanceDto dto) {
        resilience.execute(
                () -> {
                    accountApi.changeBalance(id, dto);
                    return null;
                },
                () -> null
        );
    }

    public void deleteAccount(Long userId) {
        resilience.execute(
                () -> {
                    accountApi.deleteAccount(userId);
                    return null;
                },
                () -> null
        );
    }

    public AccountDto getAccountByNumber(String number) {
        return resilience.execute(
                () -> accountApi.getAccountByNumber(number),
                this::getFallbackAccount
        );
    }

    public List<AccountDto> getAccountsByUserId(String userId) {
        return resilience.execute(
                () -> accountApi.getAccountsByUserId(userId),
                Collections::emptyList
        );
    }

    public List<AccountDto> getCurrentUserAccounts() {
        return resilience.execute(
                accountApi::getCurrentUserAccounts,
                Collections::emptyList
        );
    }

    private AccountDto getFallbackAccount() {
        return new AccountDto();
    }
}
