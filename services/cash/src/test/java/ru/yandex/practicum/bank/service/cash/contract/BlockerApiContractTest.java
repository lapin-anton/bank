package ru.yandex.practicum.bank.service.cash.contract;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import ru.yandex.practicum.bank.client.blocker.api.BlockerClient;
import ru.yandex.practicum.bank.client.blocker.model.CashCheckDto;
import ru.yandex.practicum.bank.client.blocker.model.ResultCheckDto;
import ru.yandex.practicum.bank.client.blocker.model.TransferCheckDto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(ids = "ru.yandex.practicum.bank:blocker-stubs:+:stubs:8082", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class BlockerApiContractTest {

    @Autowired
    private BlockerClient blockerClient;

    @Test
    void checkCash_returnsTrueResult() {
        CashCheckDto dto = new CashCheckDto();
        dto.setAccountNumber("40817810099910001111");
        dto.setAmount(new BigDecimal("1000.00"));

        ResultCheckDto result = assertDoesNotThrow(() -> blockerClient.checkCash(dto));
        assertNotNull(result);
    }

    @Test
    void checkTransfer_returnsTrueResult() {
        TransferCheckDto dto = new TransferCheckDto();
        dto.setFromAccount("40817810099910001111");
        dto.setToAccount("40817810099910002222");
        dto.setAmount(new BigDecimal("1500.00"));

        ResultCheckDto result = assertDoesNotThrow(() -> blockerClient.checkTransfer(dto));
        assertNotNull(result);
    }
}
