package ru.yandex.practicum.bank.service.account.integration;

import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import ru.yandex.practicum.bank.service.account.dto.ChangeBalanceDto;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTest extends AbstractIntegrationTest {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer mock.jwt.token";

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM account");
        jdbcTemplate.execute("INSERT INTO account (id, number, user_id, currency, balance, status, version) " +
                "VALUES (1, 'ACC-123', 'test-user-id', 'RUB', 100.00, 'ACTIVE', 0)");
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM account");
    }

    @Test
    void getAccount_ShouldReturnAccountDto() throws Exception {
        mockMvc.perform(get("/ACC-123")
                .header(AUTH_HEADER, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("ACC-123"))
                .andExpect(jsonPath("$.currency").value("RUB"))
                .andExpect(jsonPath("$.balance").value(100.00));
    }

    @Test
    void blockAccount_ShouldReturnOk() throws Exception {
        mockMvc.perform(patch("/1/block")
                .header(AUTH_HEADER, BEARER_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void changeBalance_ShouldUpdateAndReturnOk() throws Exception {
        ChangeBalanceDto dto = new ChangeBalanceDto();
        dto.setAmount(BigDecimal.valueOf(250.50));
        dto.setVersion(0L);

        mockMvc.perform(patch("/1/balance")
                .header(AUTH_HEADER, BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
