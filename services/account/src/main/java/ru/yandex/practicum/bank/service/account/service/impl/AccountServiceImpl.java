package ru.yandex.practicum.bank.service.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bank.common.exception.BadRequestException;
import ru.yandex.practicum.bank.common.exception.NotFoundException;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.account.dto.AccountDto;
import ru.yandex.practicum.bank.service.account.mapper.AccountMapper;
import ru.yandex.practicum.bank.service.account.model.Account;
import ru.yandex.practicum.bank.service.account.model.AccountStatus;
import ru.yandex.practicum.bank.service.account.model.Currency;
import ru.yandex.practicum.bank.service.account.repostory.AccountRepository;
import ru.yandex.practicum.bank.service.account.service.AccountService;
import ru.yandex.practicum.bank.service.account.service.NumberAccountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    private final NumberAccountService numberAccountService;

    @Override
    public List<AccountDto> getAccounts(String userId) {
        return accountRepository.getAllByUserId(userId)
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccount(String numberAccount) {

        Account account = accountRepository.getByNumber(numberAccount)
                .orElseThrow(() -> new NotFoundException("Account not found by number: %s".formatted(numberAccount)));;

        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    public AccountDto openAccount(User user, Currency currency) {

        Account account = new Account();

        if (accountRepository.existsByCurrencyAndUserId(currency, user.getId())) {
            throw new BadRequestException("Currency already exists for user");
        }

        account.setUserId(user.getId());
        account.setCurrency(currency);
        account.setNumber(numberAccountService.generateNumber());

        account = accountRepository.save(account);

        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    public void deleteAccount(User user, Long id) {
        Account account = getModel(id);

        if (!account.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("Access denied to account by id: %s".formatted(id));
        }

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new AccessDeniedException("Account balance must be exactly zero to delete");
        }

        accountRepository.delete(account);
    }

    @Override
    @Transactional
    public void blockAccount(Long id) {
        Account account = getModel(id);

        account.setStatus(AccountStatus.BLOCKED);

        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void changeBalance(Long id, BigDecimal amount, Long version) {
        Account account = getModel(id);

        if (account.getStatus().equals(AccountStatus.BLOCKED)) {
            throw new AccessDeniedException("Account is blocked");
        }

        if (!account.getVersion().equals(version)) {
            throw new AccessDeniedException("Version does not match");
        }

        account.setBalance(amount);

        accountRepository.save(account);
    }

    private Account getModel(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found by id: %s".formatted(id)));
    }
}
