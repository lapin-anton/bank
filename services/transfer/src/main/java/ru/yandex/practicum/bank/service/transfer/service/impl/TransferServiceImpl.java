package ru.yandex.practicum.bank.service.transfer.service.impl;

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
import ru.yandex.practicum.bank.client.blocker.model.ResultCheckDto;
import ru.yandex.practicum.bank.client.blocker.model.TransferCheckDto;
import ru.yandex.practicum.bank.client.exchange.api.ExchangeClient;
import ru.yandex.practicum.bank.client.exchange.model.ConvertRequestDto;
import ru.yandex.practicum.bank.client.exchange.model.ConvertResponseDto;
import ru.yandex.practicum.bank.client.exchange.model.Currency;
import ru.yandex.practicum.bank.common.model.User;
import ru.yandex.practicum.bank.service.transfer.dto.TransferDto;
import ru.yandex.practicum.bank.service.transfer.service.TransferService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final AccountClient accountClient;
    private final ExchangeClient exchangeClient;
    private final BlockerClient blockerClient;

    @Override
    @Retryable(
            include = { RuntimeException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public void transfer(TransferDto transferDto, User user) {

        AccountDto accountFrom = accountClient.getAccountByNumber(transferDto.getFromAccount());
        if (!Objects.equals(accountFrom.getUserId(), user.getId())) {
            throw new AccessDeniedException("Only the owner can write off from this account");
        }
        if (accountFrom.getBalance().compareTo(transferDto.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient funds on the account: " + transferDto.getFromAccount());
        }
        if (!check(transferDto)) {
            throw new AccessDeniedException("Operation blocked");
        }

        AccountDto accountTo = accountClient.getAccountByNumber(transferDto.getToAccount());

        ConvertRequestDto convertRequest = new ConvertRequestDto();
        convertRequest.setAmount(transferDto.getAmount());
        convertRequest.setFromCurrency(mapCurrency(accountFrom.getCurrency()));
        convertRequest.setToCurrency(mapCurrency(accountTo.getCurrency()));
        ConvertResponseDto converted = exchangeClient.convertCurrency(convertRequest);

        ChangeBalanceDto fromChange = new ChangeBalanceDto();
        fromChange.setAmount(accountFrom.getBalance().subtract(transferDto.getAmount()));
        fromChange.setVersion(accountFrom.getVersion());

        accountClient.changeBalance(accountFrom.getId(), fromChange);

        try {
            ChangeBalanceDto toChange = new ChangeBalanceDto();
            toChange.setAmount(accountTo.getBalance().add(converted.getResult()));
            toChange.setVersion(accountTo.getVersion());

            accountClient.changeBalance(accountTo.getId(), toChange);

        } catch (RuntimeException ex) {
            AccountDto updatedFrom = accountClient.getAccountByNumber(transferDto.getFromAccount());

            ChangeBalanceDto compensation = new ChangeBalanceDto();
            compensation.setAmount(updatedFrom.getBalance().add(transferDto.getAmount()));
            compensation.setVersion(updatedFrom.getVersion());

            accountClient.changeBalance(updatedFrom.getId(), compensation);

            throw new RuntimeException("Failed to credit account. Rolled back.", ex);
        }
    }

    @Recover
    public void recover(RuntimeException ex, TransferDto transferDto, User user) {
        log.error("Transfer permanently failed after retries: from={}, to={}, reason={}, user={}",
                transferDto.getFromAccount(),
                transferDto.getToAccount(),
                ex.getMessage(),
                user
        );

        throw new IllegalStateException("Transfer failed and was rolled back. Manual intervention required.", ex);
    }

    private Currency mapCurrency(ru.yandex.practicum.bank.client.account.model.Currency currency) {
        return Currency.valueOf(currency.name());
    }

    private Boolean check(TransferDto transferDto) {
        TransferCheckDto transferCheckDto = new TransferCheckDto();
        transferCheckDto.setFromAccount(transferDto.getFromAccount());
        transferCheckDto.setToAccount(transferDto.getToAccount());
        transferCheckDto.setAmount(transferDto.getAmount());

        ResultCheckDto resultCheckDto = blockerClient.checkTransfer(transferCheckDto);

        return resultCheckDto.getResult();
    }
}


