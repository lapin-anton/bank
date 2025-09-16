package ru.yandex.practicum.bank.service.blocker.service.impl;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.service.blocker.dto.CashCheckDto;
import ru.yandex.practicum.bank.service.blocker.dto.ResultCheckDto;
import ru.yandex.practicum.bank.service.blocker.dto.TransferCheckDto;
import ru.yandex.practicum.bank.service.blocker.service.CheckService;

import java.math.BigDecimal;

@Service
public class CheckServiceImpl implements CheckService {

    @Override
    public ResultCheckDto check(CashCheckDto cashCheckDto) {
        return new ResultCheckDto(check(cashCheckDto.getAmount()));
    }

    @Override
    public ResultCheckDto check(TransferCheckDto transferCheckDto) {
        return new ResultCheckDto(check(transferCheckDto.getAmount()));
    }

    private Boolean check(BigDecimal amount) {
        return amount.compareTo(new BigDecimal("1000000")) <= 0;
    }
}
