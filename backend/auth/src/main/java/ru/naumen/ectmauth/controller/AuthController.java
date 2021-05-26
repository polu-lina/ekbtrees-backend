package ru.naumen.ectmauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.naumen.ectmauth.config.OAuth2Config;
import ru.naumen.ectmauth.dto.NewUserDto;
import ru.naumen.ectmauth.service.TokensService;
import ru.naumen.ectmauth.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import static ru.naumen.ectmauth.utils.CookieUtils.setTokenCookies;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final TokensService tokensService;
    private final OAuth2Config oAuth2Config;

    @Operation(summary = "Регистрирует нового пользователя")
    @PostMapping(value = "/register")
    public void registerUser(@RequestBody NewUserDto newUserDto) {
        userService.registerNewUser(newUserDto);
    }

    @Operation(summary = "Производит аутентификацию по почте и паролю, " +
            "токены возвращаются в виде Cookie (AccessToken, RefreshToken)")
    @PostMapping("/login")
    public void login(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                      HttpServletResponse response) throws IOException {

        String encodedCredentials = authHeader.replace("Basic ", "");
        String[] decodedCredentials = new String(Base64.getUrlDecoder().decode(encodedCredentials)).split(":");

        String email = decodedCredentials[0];
        String password = decodedCredentials[1];

        try {
            setTokenCookies(response, tokensService.getTokensByCredentials(email, password));
            response.sendRedirect(oAuth2Config.getBaseUrl());
        } catch (InvalidCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
    }

    @Operation(summary = "Получение новой пары Cookie (AccessToken, RefreshToken) по старому RefreshToken")
    @PostMapping("/newTokens")
    public void newToken(@CookieValue(value = "RefreshToken") String oldRefreshToken,
                         HttpServletResponse response) throws IOException {
        try {
            setTokenCookies(response, tokensService.getTokensByRefreshToken(oldRefreshToken));
            response.sendRedirect(oAuth2Config.getBaseUrl());
        } catch (InvalidCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
    }
}
