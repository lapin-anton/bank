package ru.yandex.practicum.bank.service.account.integration;

import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import ru.yandex.practicum.bank.service.account.dto.OpenAccountDto;
import ru.yandex.practicum.bank.service.account.model.Currency;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserAccountControllerTest extends AbstractIntegrationTest {

    private final String userId = "test-user-id";

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer mock.jwt.token";

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM account");
        jdbcTemplate.execute("INSERT INTO account (id, number, user_id, currency, balance, status, version) " +
                "VALUES (1, 'ACC123', '" + userId + "', 'RUB', 0.00, 'ACTIVE', 0)");
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM account");
    }

    @Test
    void getAccounts_ShouldReturnUserAccounts() throws Exception {
        mockMvc.perform(get("/user")
                        .header(AUTH_HEADER, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(userId));
    }

    @Test
    void getAccountsById_ShouldReturnAccountsForOtherUser() throws Exception {
        mockMvc.perform(get("/user/" + userId)
                        .header(AUTH_HEADER, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(userId));
    }

    @Test
    void addAccount_ShouldCreateAccount() throws Exception {
        OpenAccountDto dto = new OpenAccountDto();
        dto.setCurrency(Currency.USD);

        mockMvc.perform(post("/user")
                        .header(AUTH_HEADER, BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    void deleteAccount_ShouldRemoveAccount() throws Exception {
        mockMvc.perform(delete("/user/1")
                        .header(AUTH_HEADER, BEARER_TOKEN))
                .andExpect(status().isOk());
    }
}
