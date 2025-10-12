package ru.yandex.practicum.bank.service.cash.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.client.account.api.AccountClient;
import ru.yandex.practicum.bank.client.account.model.AccountDto;
import ru.yandex.practicum.bank.client.account.model.ChangeBalanceDto;
import ru.yandex.practicum.bank.client.blocker.api.BlockerClient;
import ru.yandex.practicum.bank.client.blocker.model.CashCheckDto;
import ru.yandex.practicum.bank.client.blocker.model.ResultCheckDto;
import ru.yandex.practicum.bank.common.exception.BadRequestException;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.cash.dto.CashTransactionDto;
import ru.yandex.practicum.bank.service.cash.service.CashService;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashServiceImpl implements CashService {

    private final AccountClient accountClient;
    private final BlockerClient blockerClient;

    @Override
    @Retryable(
            include = { RuntimeException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public void putCash(CashTransactionDto transactionDto, User user) {

        AccountDto accountDto = getAccountByNumber(transactionDto, user);

        ChangeBalanceDto changeBalanceDto = new ChangeBalanceDto();

        changeBalanceDto.setAmount(
                transactionDto.getAmount()
                        .add(accountDto.getBalance())
        );

        changeBalanceDto.setVersion(accountDto.getVersion());

        accountClient.changeBalance(accountDto.getId(), changeBalanceDto);
    }

    @Override
    @Retryable(
            include = { RuntimeException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public void withdrawCash(CashTransactionDto transactionDto, User user) {
        AccountDto accountDto = getAccountByNumber(transactionDto, user);

        BigDecimal result = accountDto.getBalance().subtract(transactionDto.getAmount());

        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Insufficient funds in the account");
        }

        ChangeBalanceDto changeBalanceDto = new ChangeBalanceDto();

        changeBalanceDto.setAmount(result);
        changeBalanceDto.setVersion(accountDto.getVersion());

        accountClient.changeBalance(accountDto.getId(), changeBalanceDto);
    }

    @Recover
    public void recover(RuntimeException ex, CashTransactionDto transactionDto, User user) {
        log.error("Transfer permanently failed after retries: account={}, to={}, user={}",
                transactionDto.getAccountNumber(),
                ex.getMessage(),
                user
        );

        throw new IllegalStateException("Transfer failed and was rolled back. Manual intervention required.", ex);
    }

    private AccountDto getAccountByNumber(CashTransactionDto transactionDto, User user) {
        AccountDto accountDto = accountClient.getAccountByNumber(transactionDto.getAccountNumber());

        if (!Objects.equals(accountDto.getUserId(), user.getId())) {
            throw new AccessDeniedException("User is not owner of account %s".formatted(accountDto.getUserId()));
        }
        if (!check(transactionDto)) {
            throw new AccessDeniedException("Operation blocked");
        }

        return accountDto;
    }

    private Boolean check(CashTransactionDto transactionDto) {
        CashCheckDto cashCheckDto = new CashCheckDto();
        cashCheckDto.setAccountNumber(transactionDto.getAccountNumber());
        cashCheckDto.setAmount(transactionDto.getAmount());

        ResultCheckDto resultCheckDto = blockerClient.checkCash(cashCheckDto);

        return resultCheckDto.getResult();
    }
}
