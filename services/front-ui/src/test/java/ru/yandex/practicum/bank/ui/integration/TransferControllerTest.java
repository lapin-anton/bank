package ru.yandex.practicum.bank.ui.integration;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class TransferControllerTest extends AbstractIntegrationTest {

    @Test
    void transfer_ShouldRedirectToHome_WhenValid() throws Exception {
        mockMvc.perform(post("/transfer")
                        .param("fromAccount", "ACC-1")
                        .param("toAccount", "ACC-2")
                        .param("amount", "1000.00")
                        .with(mockOAuth2User())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Mockito.verify(transferClient).transfer(any());
    }

    @Test
    void transfer_ShouldReturnError_WhenInvalid() throws Exception {
        mockMvc.perform(post("/transfer")
                        .param("fromAccount", "")
                        .param("toAccount", "")
                        .param("amount", "")
                        .with(mockOAuth2User())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("formErrors"));
    }
}
