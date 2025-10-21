package ru.yandex.practicum.bank.service.blocker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.common.service.MetricService;
import ru.yandex.practicum.bank.service.blocker.dto.CashCheckDto;
import ru.yandex.practicum.bank.service.blocker.dto.ResultCheckDto;
import ru.yandex.practicum.bank.service.blocker.dto.TransferCheckDto;
import ru.yandex.practicum.bank.service.blocker.service.CheckService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService {

    private final MetricService metricService;

    @Override
    public ResultCheckDto check(CashCheckDto cashCheckDto) {
        return new ResultCheckDto(check(cashCheckDto.getAmount(), cashCheckDto.getAccountNumber()));
    }

    @Override
    public ResultCheckDto check(TransferCheckDto transferCheckDto) {
        return new ResultCheckDto(check(transferCheckDto.getAmount(), transferCheckDto.getToAccount()));
    }

    private boolean check(BigDecimal amount, String to) {
        boolean result = amount.compareTo(new BigDecimal("1000000")) <= 0;

        if (!result) {
            metricService.recordSuspiciousTransferBlocked(to);
        }

        return result;
    }
}
