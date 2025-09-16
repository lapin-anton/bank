package ru.yandex.practicum.bank.ui.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.yandex.practicum.bank.client.account.api.AccountClient;
import ru.yandex.practicum.bank.client.cash.api.CashClient;
import ru.yandex.practicum.bank.client.transfer.api.TransferClient;
import ru.yandex.practicum.bank.ui.config.MockClientConfig;
import ru.yandex.practicum.bank.ui.config.MockFeignSecurityConfig;
import ru.yandex.practicum.bank.ui.config.MockKeycloakConfig;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({
        MockClientConfig.class,
        MockFeignSecurityConfig.class,
        MockKeycloakConfig.class
})
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected AccountClient accountClient;

    @Autowired
    protected CashClient cashClient;

    @Autowired
    protected TransferClient transferClient;

    protected final String userId = "test-user-id";
    protected final String email = "user@bank.test";
    protected final String name = "Testy";
    protected final String family = "McUser";

    protected RequestPostProcessor mockOAuth2User() {
        return oauth2Login().attributes(attrs -> {
            attrs.put("sub", userId);
            attrs.put("preferred_username", email.split("@")[0]);
            attrs.put("email", email);
            attrs.put("given_name", name);
            attrs.put("family_name", family);
            attrs.put("birth_date", "1990-01-01");
        });
    }

}
