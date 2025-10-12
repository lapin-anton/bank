package ru.yandex.practicum.bank.ui.integration;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.yandex.practicum.bank.client.account.model.AccountDto;
import ru.yandex.practicum.bank.client.account.model.Currency;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class AccountControllerTest extends AbstractIntegrationTest {

    @Test
    void addAccount_ShouldRedirectToHome_WhenValid() throws Exception {
        mockMvc.perform(post("/account")
                        .param("currency", "USD")
                        .with(mockOAuth2User())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Mockito.verify(accountClient).addAccount(any());
    }

    @Test
    void addAccount_ShouldReturnErrorView_WhenInvalid() throws Exception {
        mockMvc.perform(post("/account")
                        .param("currency", "")
                        .with(mockOAuth2User())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("formErrors"));
    }

    @Test
    void deleteAccount_ShouldRedirectToHome() throws Exception {
        mockMvc.perform(post("/account/delete/1")
                        .with(mockOAuth2User())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Mockito.verify(accountClient).deleteAccount(1L);
    }

    @Test
    void getAccounts_ShouldReturnJson() throws Exception {
        AccountDto dto = new AccountDto();
        dto.setId(1L);
        dto.setNumber("ACC123");
        dto.setUserId(userId);
        dto.setCurrency(Currency.USD);

        Mockito.when(accountClient.getAccountsByUserId(userId)).thenReturn(List.of(dto));

        mockMvc.perform(get("/account/account/user/" + userId)
                        .with(mockOAuth2User()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].currency").value("USD"));
    }
}
