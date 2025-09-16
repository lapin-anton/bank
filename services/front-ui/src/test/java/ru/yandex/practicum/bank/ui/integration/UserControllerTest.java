package ru.yandex.practicum.bank.ui.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends AbstractIntegrationTest {

    @Test
    void edit_ShouldReturnError_WhenInvalid() throws Exception {
        mockMvc.perform(post("/user/edit")
                .param("name", "")
                .param("familyName", "")
                .param("email", "")
                .param("birthDate", "")
                .with(mockOAuth2User())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("formErrors"));
    }

    @Test
    void password_ShouldReturnError_WhenInvalid() throws Exception {
        mockMvc.perform(post("/user/password")
                .param("password", "")
                .with(mockOAuth2User())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("formErrors"));
    }

    @Test
    void forceLogout_ShouldRedirectToAuthorization() throws Exception {
        mockMvc.perform(get("/user/force-logout")
                .with(mockOAuth2User()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(HttpHeaders.LOCATION, "/oauth2/authorization/keycloak"));
    }
}
