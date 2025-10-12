package ru.yandex.practicum.bank.service.generator.exchange.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.client.exchange.model.Currency;
import ru.yandex.practicum.bank.common.massage.Rate;
import ru.yandex.practicum.bank.service.generator.exchange.scheduler.GeneratorExchangeScheduler;

import java.math.BigDecimal;
import java.util.Objects;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig(classes = GeneratorExchangeSchedulerTest.Config.class)
class GeneratorExchangeSchedulerTest {

    @Configuration
    static class Config {
        @Bean
        public KafkaTemplate<String, Rate> kafkaTemplate() {
            return mock(KafkaTemplate.class);
        }

        @Bean GeneratorExchangeScheduler generatorExchangeScheduler(KafkaTemplate<String, Rate> kafkaTemplate) {
            return new GeneratorExchangeScheduler(kafkaTemplate);
        }
    }

    @Autowired
    private KafkaTemplate<String, Rate> kafkaTemplate;

    @Autowired
    private GeneratorExchangeScheduler generatorExchangeScheduler;

    @BeforeEach
    void setup() {
        reset(kafkaTemplate);
    }

    @Test
    void run_ShouldSendRateDtoForAllCurrencies() {
        generatorExchangeScheduler.run();

        for (Currency currency : Currency.values()) {
            verify(kafkaTemplate, atLeastOnce()).send(
                    anyString(),
                    argThat(key -> key != null && !key.isEmpty()),
                    argThat(rate ->
                            rate != null &&
                                    Objects.equals(rate.getCurrency(), currency.getValue()) &&
                                    rate.getValue() != null &&
                                    rate.getValue().compareTo(BigDecimal.ZERO) > 0
                    )
            );
        }

        verifyNoMoreInteractions(kafkaTemplate);
    }
}
