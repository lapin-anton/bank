package ru.yandex.practicum.bank.service.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.service.account.repostory.AccountRepository;
import ru.yandex.practicum.bank.service.account.service.NumberAccountService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class NumberAccountServiceImpl implements NumberAccountService {

    private static final String PREFIX = "ACC";

    private final AccountRepository accountRepository;

    @Override
    public String generateNumber() {

        LocalDate today = LocalDate.now();

        long count = accountRepository.count() + 1;

        String datePart = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String numberPart = String.format("%05d", count);

        return PREFIX + "-" + datePart + "-" + numberPart;
    }
}
