package ru.yandex.practicum.bank.ui.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;
import ru.yandex.practicum.bank.ui.config.KeycloakAdminProperty;
import ru.yandex.practicum.bank.ui.dto.PasswordUserFormDto;
import ru.yandex.practicum.bank.ui.dto.UserFormDto;
import ru.yandex.practicum.bank.ui.service.UserService;
import ru.yandex.practicum.bank.common.annotation.CurrentUser;
import ru.yandex.practicum.bank.common.model.User;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final KeycloakAdminProperty keycloakAdminProperty;

    @PostMapping("/edit")
    public String edit(@CurrentUser User user, @Valid @ModelAttribute("userFrom") UserFormDto userFormDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("formErrors", result.getAllErrors());

            return "error";
        }

        userService.edit(user, userFormDto);

        return "redirect:/user/force-logout";
    }

    @PostMapping("/password")
    public String edit(@CurrentUser User user, @Valid @ModelAttribute("passwordFrom") PasswordUserFormDto passwordUserFormDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("formErrors", result.getAllErrors());

            return "error";
        }

        userService.password(user, passwordUserFormDto);

        return "redirect:/user/force-logout";
    }

    /**
     * Без очистки сессии в keycloak для обновления токена
     */
    @GetMapping("/force-logout")
    public ResponseEntity<Void> forceLogout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);

        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .header(HttpHeaders.LOCATION, "/oauth2/authorization/keycloak")
                .build();
    }

    /**
     * Полный выход с очисткой сессии везде
     */
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request,
                                       HttpServletResponse response,
                                       OAuth2AuthenticationToken authToken) {
        OidcUser oidcUser = (OidcUser) authToken.getPrincipal();
        String idToken = oidcUser.getIdToken().getTokenValue();

        new SecurityContextLogoutHandler().logout(request, response, null);

        String redirect = "%s://%s:%s".formatted(request.getScheme(), request.getServerName(), request.getServerPort());

        String logoutUri = UriComponentsBuilder
                .fromUriString("%s/realms/%s/protocol/openid-connect/logout".formatted(keycloakAdminProperty.getServerUrl(), keycloakAdminProperty.getRealm()))
                .queryParam("post_logout_redirect_uri", redirect)
                .queryParam("id_token_hint", idToken)
                .build()
                .toUriString();

        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .header(HttpHeaders.LOCATION, logoutUri)
                .build();
    }
}