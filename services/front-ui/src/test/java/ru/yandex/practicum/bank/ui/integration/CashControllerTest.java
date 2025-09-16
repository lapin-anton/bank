package ru.yandex.practicum.bank.ui.integration;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CashControllerTest extends AbstractIntegrationTest {

    @Test
    void putCash_ShouldRedirectToHome_WhenValid() throws Exception {
        mockMvc.perform(post("/cash/put")
                .param("accountNumber", "ACC-123")
                .param("amount", "5000.00")
                .with(mockOAuth2User())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Mockito.verify(cashClient).putCash(any());
    }

    @Test
    void putCash_ShouldReturnError_WhenInvalid() throws Exception {
        mockMvc.perform(post("/cash/put")
                .param("accountNumber", "")
                .param("amount", "")
                .with(mockOAuth2User())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("formErrors"));
    }

    @Test
    void withdrawCash_ShouldRedirectToHome_WhenValid() throws Exception {
        mockMvc.perform(post("/cash/withdraw")
                .param("accountNumber", "ACC-456")
                .param("amount", "1500.00")
                .with(mockOAuth2User())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Mockito.verify(cashClient).withdrawCash(any());
    }

    @Test
    void withdrawCash_ShouldReturnError_WhenInvalid() throws Exception {
        mockMvc.perform(post("/cash/withdraw")
                .param("accountNumber", "")
                .param("amount", "")
                .with(mockOAuth2User())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("formErrors"));
    }
}
