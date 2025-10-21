package ru.yandex.practicum.bank.service.blocker.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.bank.service.blocker.dto.CashCheckDto;
import ru.yandex.practicum.bank.service.blocker.dto.ResultCheckDto;
import ru.yandex.practicum.bank.service.blocker.dto.TransferCheckDto;
import ru.yandex.practicum.bank.service.blocker.service.CheckService;
import ru.yandex.practicum.bank.service.blocker.service.impl.CheckServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = CheckServiceTest.Config.class)
class CheckServiceTest {

    @Configuration
    static class Config {
        @Bean CheckService checkService() {
            return new CheckServiceImpl();
        }
    }

    @Autowired
    private CheckService checkService;

    @Test
    void checkCash_ShouldReturnTrue_WhenAmountBelowLimit() {
        CashCheckDto dto = new CashCheckDto();
        dto.setAmount(new BigDecimal("999999"));

        ResultCheckDto result = checkService.check(dto);

        assertTrue(result.getResult());
    }

    @Test
    void checkCash_ShouldReturnFalse_WhenAmountExceedsLimit() {
        CashCheckDto dto = new CashCheckDto();
        dto.setAmount(new BigDecimal("1000001"));

        ResultCheckDto result = checkService.check(dto);

        assertFalse(result.getResult());
    }

    @Test
    void checkTransfer_ShouldReturnTrue_WhenAmountBelowLimit() {
        TransferCheckDto dto = new TransferCheckDto();
        dto.setAmount(new BigDecimal("123456"));

        ResultCheckDto result = checkService.check(dto);

        assertTrue(result.getResult());
    }

    @Test
    void checkTransfer_ShouldReturnFalse_WhenAmountExceedsLimit() {
        TransferCheckDto dto = new TransferCheckDto();
        dto.setAmount(new BigDecimal("5000000"));

        ResultCheckDto result = checkService.check(dto);

        assertFalse(result.getResult());
    }
}
