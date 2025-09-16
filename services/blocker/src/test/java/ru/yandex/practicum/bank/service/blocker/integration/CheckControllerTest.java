package ru.yandex.practicum.bank.service.blocker.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.bank.service.blocker.config.JwtTestConfig;
import ru.yandex.practicum.bank.service.blocker.dto.CashCheckDto;
import ru.yandex.practicum.bank.service.blocker.dto.TransferCheckDto;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(JwtTestConfig.class)
class CheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer mock.jwt.token";

    @Test
    void checkCash_ShouldReturnTrue_ForValidAmount() throws Exception {
        CashCheckDto dto = new CashCheckDto("ACC-1", new BigDecimal("999999.99"));

        mockMvc.perform(post("/cash")
                .header(AUTH_HEADER, BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void checkCash_ShouldReturnFalse_ForExcessAmount() throws Exception {
        CashCheckDto dto = new CashCheckDto("ACC-1", new BigDecimal("1000000.01"));

        mockMvc.perform(post("/cash")
                .header(AUTH_HEADER, BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(false));
    }

    @Test
    void checkTransfer_ShouldReturnTrue_ForValidAmount() throws Exception {
        TransferCheckDto dto = new TransferCheckDto();
        dto.setFromAccount("ACC-1");
        dto.setToAccount("ACC-2");
        dto.setAmount(new BigDecimal("500000.00"));

        mockMvc.perform(post("/transfer")
                .header(AUTH_HEADER, BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void checkTransfer_ShouldReturnFalse_ForExcessAmount() throws Exception {
        TransferCheckDto dto = new TransferCheckDto();
        dto.setFromAccount("ACC-1");
        dto.setToAccount("ACC-2");
        dto.setAmount(new BigDecimal("1000000.01"));

        mockMvc.perform(post("/transfer")
                .header(AUTH_HEADER, BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(false));
    }

    @Test
    void checkCash_ShouldFailValidation_WhenAccountNumberMissing() throws Exception {
        CashCheckDto dto = new CashCheckDto("", new BigDecimal("1000"));

        mockMvc.perform(post("/cash")
                .header(AUTH_HEADER, BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkTransfer_ShouldFailValidation_WhenAmountMissing() throws Exception {
        TransferCheckDto dto = new TransferCheckDto();
        dto.setFromAccount("A1");
        dto.setToAccount("A2");
        dto.setAmount(null);

        mockMvc.perform(post("/transfer")
                .header(AUTH_HEADER, BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
