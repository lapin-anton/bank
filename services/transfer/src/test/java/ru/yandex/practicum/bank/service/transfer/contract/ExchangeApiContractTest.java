package ru.yandex.practicum.bank.service.transfer.contract;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import ru.yandex.practicum.bank.client.exchange.api.ExchangeClient;
import ru.yandex.practicum.bank.client.exchange.model.ConvertRequestDto;
import ru.yandex.practicum.bank.client.exchange.model.ConvertResponseDto;
import ru.yandex.practicum.bank.client.exchange.model.Currency;
import ru.yandex.practicum.bank.client.exchange.model.RateDto;
import ru.yandex.practicum.bank.client.exchange.model.UpdateExchangeRate200Response;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum.bank:exchange-stubs:+:stubs:8081",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class ExchangeApiContractTest {

    @Autowired
    private ExchangeClient exchangeClient;

    @Test
    void convertCurrency_returnsValidResult() {
        ConvertRequestDto dto = new ConvertRequestDto();
        dto.setFromCurrency(Currency.USD);
        dto.setToCurrency(Currency.RUB);
        dto.setAmount(new BigDecimal("5000.00"));

        assertDoesNotThrow(() -> exchangeClient.convertCurrency(dto));
    }

    @Test
    void getExchangeRates_returnsList() {
        List<?> rates = assertDoesNotThrow(exchangeClient::getExchangeRates);
        assertNotNull(rates);
    }

    @Test
    void updateExchangeRate_returnsSuccess() {
        RateDto rateDto = new RateDto();
        rateDto.setCurrency(Currency.USD);
        rateDto.setValue(new BigDecimal("92.50"));

        UpdateExchangeRate200Response response = assertDoesNotThrow(() -> exchangeClient.updateExchangeRate(rateDto));
        assertNotNull(response);
    }
}
