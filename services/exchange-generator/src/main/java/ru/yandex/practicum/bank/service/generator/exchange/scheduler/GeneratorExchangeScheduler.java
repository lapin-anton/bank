package ru.yandex.practicum.bank.service.generator.exchange.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.bank.client.exchange.model.Currency;
import ru.yandex.practicum.bank.common.config.KafkaConfig;
import ru.yandex.practicum.bank.common.message.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GeneratorExchangeScheduler {

    private final KafkaTemplate<String, Rate> orderKafkaTemplate;

    private final Random random = new Random();

    @Scheduled(fixedRate = 1000)
    public void run() {
        for (Currency currency : Currency.values()) {

            Rate rateMessage = Rate.builder()
                    .id(UUID.randomUUID())
                    .currency(currency.getValue())
                    .value(generateRandomRate(currency))
                    .build();

            orderKafkaTemplate.send(KafkaConfig.RATE_TOPIC, rateMessage.getId().toString(), rateMessage);
        }
    }

    private BigDecimal generateRandomRate(Currency currency) {
        double min;
        double max;

        switch (currency) {
            case RUB -> {
                min = 1.0;
                max = 1.0;
            }
            case USD -> {
                min = 75.0;
                max = 80.0;
            }
            case EUR -> {
                min = 90.0;
                max = 95.0;
            }
            default -> {
                min = 50.0;
                max = 120.0;
            }
        }

        double randomValue = min + (max - min) * random.nextDouble();
        return BigDecimal.valueOf(randomValue).setScale(2, RoundingMode.HALF_UP);
    }
}
