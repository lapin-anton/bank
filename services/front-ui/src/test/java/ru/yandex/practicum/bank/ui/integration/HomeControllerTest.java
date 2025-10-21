package ru.yandex.practicum.bank.ui.integration;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.yandex.practicum.bank.client.account.model.AccountDto;
import ru.yandex.practicum.bank.client.account.model.Currency;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HomeControllerTest extends AbstractIntegrationTest {

    @Test
    void home_ShouldReturnHomeView_WithAllModels() throws Exception {
        AccountDto account = new AccountDto();
        account.setId(1L);
        account.setNumber("ACC-1");
        account.setUserId(userId);
        account.setCurrency(Currency.USD);
        account.setBalance(BigDecimal.valueOf(5000));
        account.setVersion(1L);
        account.setStatus(ru.yandex.practicum.bank.client.account.model.AccountStatus.ACTIVE);

        Mockito.when(accountClient.getCurrentUserAccounts()).thenReturn(List.of(account));


        mockMvc.perform(get("/")
                        .with(mockOAuth2User()))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("userFrom"))
                .andExpect(model().attributeExists("passwordFrom"))
                .andExpect(model().attributeExists("accounts"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("cashTransaction"))
                .andExpect(model().attributeExists("transfer"));
    }
}
