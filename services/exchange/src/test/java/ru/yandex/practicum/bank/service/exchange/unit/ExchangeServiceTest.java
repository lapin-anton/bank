package ru.yandex.practicum.bank.service.exchange.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.service.exchange.dto.ConvertRequestDto;
import ru.yandex.practicum.bank.service.exchange.dto.ConvertResponseDto;
import ru.yandex.practicum.bank.service.exchange.dto.RateDto;
import ru.yandex.practicum.bank.service.exchange.model.Currency;
import ru.yandex.practicum.bank.service.exchange.model.ExchangeRate;
import ru.yandex.practicum.bank.service.exchange.repository.ExchangeRateRepository;
import ru.yandex.practicum.bank.service.exchange.service.ExchangeService;
import ru.yandex.practicum.bank.service.exchange.service.impl.ExchangeServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = ExchangeServiceTest.Config.class)
class ExchangeServiceTest {

    @Configuration
    static class Config {
        @Bean ExchangeRateRepository exchangeRateRepository() { return mock(ExchangeRateRepository.class); }
        @Bean ExchangeService exchangeService(ExchangeRateRepository repo) {
            return new ExchangeServiceImpl(repo);
        }
    }

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private ExchangeService exchangeService;

    private ExchangeRate usdRate;
    private ExchangeRate eurRate;
    private ExchangeRate rubRate;

    @BeforeEach
    void setup() {
        reset(exchangeRateRepository);

        usdRate = new ExchangeRate();
        usdRate.setCurrency(Currency.USD);
        usdRate.setValue(new BigDecimal("100"));

        eurRate = new ExchangeRate();
        eurRate.setCurrency(Currency.EUR);
        eurRate.setValue(new BigDecimal("120"));

        rubRate = new ExchangeRate();
        rubRate.setCurrency(Currency.RUB);
        rubRate.setValue(BigDecimal.ONE);
    }

    @Test
    void convert_ShouldReturnSameAmount_WhenCurrenciesEqual() {
        ConvertRequestDto request = new ConvertRequestDto();
        request.setFromCurrency(Currency.RUB);
        request.setToCurrency(Currency.RUB);
        request.setAmount(new BigDecimal("1000"));

        ConvertResponseDto response = exchangeService.convert(request);

        assertEquals(0, response.getResult().compareTo(new BigDecimal("1000")));
        verify(exchangeRateRepository, never()).findByCurrency(any());
    }

    @Test
    void convert_ShouldConvertFromUsdToRub() {
        ConvertRequestDto request = new ConvertRequestDto();
        request.setFromCurrency(Currency.USD);
        request.setToCurrency(Currency.RUB);
        request.setAmount(new BigDecimal("10"));

        when(exchangeRateRepository.findByCurrency(Currency.USD)).thenReturn(Optional.of(usdRate));
        when(exchangeRateRepository.findByCurrency(Currency.RUB)).thenReturn(Optional.of(rubRate));

        ConvertResponseDto response = exchangeService.convert(request);

        assertEquals(0, response.getResult().compareTo(new BigDecimal("1000.00")));
    }

    @Test
    void convert_ShouldConvertFromRubToEur() {
        ConvertRequestDto request = new ConvertRequestDto();
        request.setFromCurrency(Currency.RUB);
        request.setToCurrency(Currency.EUR);
        request.setAmount(new BigDecimal("240"));

        when(exchangeRateRepository.findByCurrency(Currency.EUR)).thenReturn(Optional.of(eurRate));
        when(exchangeRateRepository.findByCurrency(Currency.RUB)).thenReturn(Optional.of(rubRate));

        ConvertResponseDto response = exchangeService.convert(request);

        assertEquals(0, response.getResult().compareTo(new BigDecimal("2.00")));
    }

    @Test
    void convert_ShouldConvertFromUsdToEur() {
        ConvertRequestDto request = new ConvertRequestDto();
        request.setFromCurrency(Currency.USD);
        request.setToCurrency(Currency.EUR);
        request.setAmount(new BigDecimal("10"));

        when(exchangeRateRepository.findByCurrency(Currency.USD)).thenReturn(Optional.of(usdRate));
        when(exchangeRateRepository.findByCurrency(Currency.EUR)).thenReturn(Optional.of(eurRate));

        ConvertResponseDto response = exchangeService.convert(request);

        assertEquals(0, response.getResult().compareTo(new BigDecimal("8.33")));
    }

    @Test
    void convert_ShouldThrow_WhenFromRateMissing() {
        ConvertRequestDto request = new ConvertRequestDto();
        request.setFromCurrency(Currency.USD);
        request.setToCurrency(Currency.RUB);
        request.setAmount(new BigDecimal("10"));

        when(exchangeRateRepository.findByCurrency(Currency.USD)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                exchangeService.convert(request));

        assertTrue(ex.getMessage().contains("No course found for USD"));
    }

    @Test
    void acceptRate_ShouldSaveNewRate() {
        RateDto dto = new RateDto();
        dto.setCurrency(Currency.EUR);
        dto.setValue(new BigDecimal("123.45"));

        when(exchangeRateRepository.findByCurrency(Currency.EUR)).thenReturn(Optional.empty());

        exchangeService.acceptRate(dto);

        verify(exchangeRateRepository).save(argThat(rate ->
                rate.getCurrency() == Currency.EUR &&
                        rate.getValue().compareTo(new BigDecimal("123.45")) == 0
        ));
    }

    @Test
    void acceptRate_ShouldUpdateExistingRate() {
        RateDto dto = new RateDto();
        dto.setCurrency(Currency.USD);
        dto.setValue(new BigDecimal("150.00"));

        when(exchangeRateRepository.findByCurrency(Currency.USD)).thenReturn(Optional.of(usdRate));

        exchangeService.acceptRate(dto);

        verify(exchangeRateRepository).save(argThat(rate ->
                rate.getCurrency() == Currency.USD &&
                        rate.getValue().compareTo(new BigDecimal("150.00")) == 0
        ));
    }

    @Test
    void rates_ShouldReturnListWithoutRUB() {
        ExchangeRate usd = new ExchangeRate();
        usd.setCurrency(Currency.USD);
        usd.setValue(BigDecimal.valueOf(100));

        ExchangeRate rub = new ExchangeRate();
        rub.setCurrency(Currency.RUB);
        rub.setValue(BigDecimal.ONE);

        when(exchangeRateRepository.findAll()).thenReturn(List.of(usd, rub));

        List<RateDto> result = exchangeService.rates();

        assertEquals(1, result.size());
        assertEquals(Currency.USD, result.getFirst().getCurrency());
    }
}
