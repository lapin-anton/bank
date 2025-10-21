package ru.yandex.practicum.bank.service.account.service;

import ru.yandex.practicum.bank.service.account.dto.AccountDto;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.account.model.Currency;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<AccountDto> getAccounts(String userId);

    AccountDto getAccount(String numberAccount);

    AccountDto openAccount(User user, Currency currency);

    void deleteAccount(User user, Long id);

    void blockAccount(Long id);

    void changeBalance(Long id, BigDecimal amount, Long version);
}
