package ru.yandex.practicum.bank.service.exchange.service;

import ru.yandex.practicum.bank.service.exchange.dto.ConvertRequestDto;
import ru.yandex.practicum.bank.service.exchange.dto.ConvertResponseDto;
import ru.yandex.practicum.bank.service.exchange.dto.RateDto;

import java.util.List;

public interface ExchangeService {
    ConvertResponseDto convert(ConvertRequestDto convertRequestDto);

    void acceptRate(RateDto rateDto);

    List<RateDto> rates();
}
