package ru.yandex.practicum.bank.ui.contract;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import ru.yandex.practicum.bank.client.transfer.api.TransferClient;
import ru.yandex.practicum.bank.client.transfer.model.TransferDto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum.bank:transfer-stubs:+:stubs:8084",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class TransferApiContractTest {

    @Autowired
    private TransferClient transferClient;

    @Test
    void transfer_executesSuccessfully() {
        TransferDto dto = new TransferDto();
        dto.setFromAccount("40817810099910001111");
        dto.setToAccount("40817810099910002222");
        dto.setAmount(new BigDecimal("5000.00"));

        assertDoesNotThrow(() -> transferClient.transfer(dto));
    }
}

