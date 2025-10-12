package ru.yandex.practicum.bank.service.exchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bank.common.config.KafkaConfig;
import ru.yandex.practicum.bank.common.massage.Rate;
import ru.yandex.practicum.bank.service.exchange.dto.ConvertRequestDto;
import ru.yandex.practicum.bank.service.exchange.dto.ConvertResponseDto;
import ru.yandex.practicum.bank.service.exchange.dto.RateDto;
import ru.yandex.practicum.bank.service.exchange.model.Currency;
import ru.yandex.practicum.bank.service.exchange.model.ExchangeRate;
import ru.yandex.practicum.bank.service.exchange.repository.ExchangeRateRepository;
import ru.yandex.practicum.bank.service.exchange.service.ExchangeService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private static final Currency base = Currency.RUB;

    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    @Transactional
    public ConvertResponseDto convert(ConvertRequestDto convertRequestDto) {
        Currency from = convertRequestDto.getFromCurrency();
        Currency to = convertRequestDto.getToCurrency();
        BigDecimal amount = convertRequestDto.getAmount();

        if (from.equals(to)) {
            return new ConvertResponseDto(amount);
        }

        BigDecimal fromRate = exchangeRateRepository.findByCurrency(from)
                .orElseThrow(() -> new RuntimeException("No course found for " + from))
                .getValue();

        BigDecimal toRate = exchangeRateRepository.findByCurrency(to)
                .orElseThrow(() -> new RuntimeException("No course found for " + to))
                .getValue();

        BigDecimal inRub = from.equals(base) ? amount : amount.multiply(fromRate);
        BigDecimal result = to.equals(base) ? inRub : inRub.divide(toRate, 2, RoundingMode.HALF_UP);

        return new ConvertResponseDto(result);
    }

    @Override
    @Transactional
    public void acceptRate(RateDto rateDto) {
        ExchangeRate exchangeRate = exchangeRateRepository.findByCurrency(rateDto.getCurrency())
                .orElse(new ExchangeRate());

        exchangeRate.setCurrency(rateDto.getCurrency());
        exchangeRate.setValue(rateDto.getValue());

        exchangeRateRepository.save(exchangeRate);
    }

    @Override
    @Transactional
    @KafkaListener(
            groupId = KafkaConfig.RATE_GROPE,
            topics = KafkaConfig.RATE_TOPIC
    )
    public void acceptRate(ConsumerRecord<String, Rate> rateConsumerRecord) {

        Currency currency = Currency.valueOf(rateConsumerRecord.value().getCurrency());

        ExchangeRate exchangeRate = exchangeRateRepository.findByCurrency(currency)
                .orElse(new ExchangeRate());

        exchangeRate.setCurrency(currency);
        exchangeRate.setValue(rateConsumerRecord.value().getValue());

        exchangeRateRepository.save(exchangeRate);
    }

    @Override
    public List<RateDto> rates() {
        return exchangeRateRepository.findAll()
                .stream()
                .filter(exchange -> !exchange.getCurrency().equals(base))
                .map(RateDto::of)
                .toList();
    }
}
