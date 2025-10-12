package ru.yandex.practicum.bank.ui.contract;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import ru.yandex.practicum.bank.client.cash.api.CashClient;
import ru.yandex.practicum.bank.client.cash.model.CashTransactionDto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum.bank:cash-stubs:+:stubs:8083",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class CashApiContractTest {

    @Autowired
    private CashClient cashClient;

    @Test
    void putCash_executesSuccessfully() {
        CashTransactionDto dto = new CashTransactionDto();
        dto.setAccountNumber("40817810099910004321");
        dto.setAmount(new BigDecimal("2500.00"));

        assertDoesNotThrow(() -> cashClient.putCash(dto));
    }

    @Test
    void withdrawCash_executesSuccessfully() {
        CashTransactionDto dto = new CashTransactionDto();
        dto.setAccountNumber("40817810099910004321");
        dto.setAmount(new BigDecimal("2500.00"));

        assertDoesNotThrow(() -> cashClient.withdrawCash(dto));
    }
}
