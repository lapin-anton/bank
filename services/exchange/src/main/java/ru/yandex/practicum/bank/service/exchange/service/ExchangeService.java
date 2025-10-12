package ru.yandex.practicum.bank.service.exchange.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import ru.yandex.practicum.bank.common.message.Rate;
import ru.yandex.practicum.bank.service.exchange.dto.ConvertRequestDto;
import ru.yandex.practicum.bank.service.exchange.dto.ConvertResponseDto;
import ru.yandex.practicum.bank.service.exchange.dto.RateDto;

import java.util.List;

public interface ExchangeService {
    ConvertResponseDto convert(ConvertRequestDto convertRequestDto);

    void acceptRate(RateDto rateDto);

    void acceptRate(ConsumerRecord<String, Rate> record);

    List<RateDto> rates();
}
