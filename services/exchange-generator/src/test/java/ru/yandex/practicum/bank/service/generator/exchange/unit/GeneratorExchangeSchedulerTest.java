package ru.yandex.practicum.bank.service.generator.exchange.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.client.exchange.api.ExchangeClient;
import ru.yandex.practicum.bank.client.exchange.model.Currency;
import ru.yandex.practicum.bank.service.generator.exchange.scheduler.GeneratorExchangeScheduler;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = GeneratorExchangeSchedulerTest.Config.class)
class GeneratorExchangeSchedulerTest {

    @Configuration
    static class Config {
        @Bean
        ExchangeClient exchangeClient() {
            return mock(ExchangeClient.class);
        }

        @Bean
        GeneratorExchangeScheduler generatorExchangeScheduler(ExchangeClient exchangeClient) {
            return new GeneratorExchangeScheduler(exchangeClient);
        }
    }

    @Autowired
    private ExchangeClient exchangeClient;

    @Autowired
    private GeneratorExchangeScheduler generatorExchangeScheduler;

    @BeforeEach
    void setup() {
        reset(exchangeClient);
    }

    @Test
    void run_ShouldSendRateDtoForAllCurrencies() {
        generatorExchangeScheduler.run();

        for (Currency currency : Currency.values()) {
            verify(exchangeClient).updateExchangeRate(argThat(dto -> dto.getCurrency() == currency &&
                    dto.getValue() != null &&
                    dto.getValue().compareTo(BigDecimal.ZERO) > 0));
        }

        verifyNoMoreInteractions(exchangeClient);
    }
}
