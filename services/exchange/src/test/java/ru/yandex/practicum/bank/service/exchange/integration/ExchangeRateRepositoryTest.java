package ru.yandex.practicum.bank.service.exchange.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bank.service.exchange.model.Currency;
import ru.yandex.practicum.bank.service.exchange.model.ExchangeRate;
import ru.yandex.practicum.bank.service.exchange.repository.ExchangeRateRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class ExchangeRateRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Test
    void saveAndFindByCurrency() {
        ExchangeRate usdRate = new ExchangeRate();
        usdRate.setCurrency(Currency.USD);
        usdRate.setValue(new BigDecimal("91.12"));

        exchangeRateRepository.save(usdRate);

        Optional<ExchangeRate> found = exchangeRateRepository.findByCurrency(Currency.USD);

        assertThat(found).isPresent();
        assertThat(found.get().getValue()).isEqualByComparingTo("91.12");
        assertThat(found.get().getCurrency()).isEqualTo(Currency.USD);
    }

    @Test
    void findByCurrency_NotFound() {
        Optional<ExchangeRate> missing = exchangeRateRepository.findByCurrency(Currency.EUR);

        assertThat(missing).isEmpty();
    }
}
