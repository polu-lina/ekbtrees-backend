package ru.naumen.ectmauth.controller;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.servlet.ServletException;

import io.jsonwebtoken.*;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.naumen.ectmauth.utils.HashingAssignment;
import ru.naumen.ectmauth.entity.Provider;
import ru.naumen.ectmauth.entity.User;
import ru.naumen.ectmauth.service.JWTService;
import ru.naumen.ectmauth.service.TokenService;
import ru.naumen.ectmauth.service.UserServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "UserController", produces = "application/json")
@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/auth")
public class UserController {


    private final UserServiceImpl userService;
    private final TokenService tokenService;
    private final JWTService jwtService;

    private static final String JWT_PASSWORD = "bm5n3SkxCX4kKRy4";


    @Operation(summary = "Ожидает почту и пароль, чтоб создать нового пользователя")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) throws ServletException, NoSuchAlgorithmException {

        User userTemp = userService.findByEmail(user.getEmail());
        if (userTemp != null) {
            throw new ServletException("This email are already used");
        }
        String hashedPassword = HashingAssignment.getHash(user.getPassword(), "SHA-256");
        User hashedUser = user;
        hashedUser.setPassword(hashedPassword);
        hashedUser.setProvider(Provider.LOCAL);

        return userService.save(hashedUser);
    }


    @Operation(summary = "Ожидает почту и пароль пользователя, чтоб войти и получить токены")
    @PostMapping("/login")
    @ResponseBody
    public void login( @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String headerStr, HttpServletRequest request, HttpServletResponse response) throws ServletException, NoSuchAlgorithmException {


      /*  String encodedCredentials = request
                .getHeader(HttpHeaders.AUTHORIZATION)
                .replace("Basic ", "");*/
        String encodedCredentials=headerStr.replace("Basic ", "");

        String[] decodedCredentials = new String(Base64.getUrlDecoder().decode(encodedCredentials)).split(":");


        String email = decodedCredentials[0];
        String password = decodedCredentials[1];
     /*   String email = json.get("email");
        String password = json.get("password");
        if (email == null || password == null) {
            throw new ServletException("Please fill in username and password");
        }*/

        System.out.println("Hello "+email+" "+password);

        User user = userService.findByEmail(email);

        if (user == null) {
            throw new ServletException("User email not found.");
        }

        String pwd = user.getPassword();

        if (!HashingAssignment.getHash(password, "SHA-256").equalsIgnoreCase(pwd)) {
            throw new ServletException("Invalid login. Please check your name and password.");
        }

        System.out.println("Hello "+email+" "+user.getFirstName());
        Map<String, String> tokens = jwtService.createNewTokens(user.getUser_id(), email, user.getFirstName(), user.getLastName(), user.getProvider());

        Cookie cookie_access_token = new Cookie("access_token", tokens.get("access_token"));
        cookie_access_token.setHttpOnly(true);
        response.addCookie(cookie_access_token);
        Cookie cookie_refresh_token = new Cookie("refresh_token", tokens.get("refresh_token"));
        cookie_refresh_token.setHttpOnly(true);
        response.addCookie(cookie_refresh_token);

    }

   /* @Operation(summary = "Выдает новые токены в куки")
    @PostMapping("/newToken")
    @ResponseBody
    public void newToken(@RequestBody(required = false) Map<String, String> json, HttpServletResponse response) {
        if (json == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());

        }

        String email;
        String refreshToken;
        try {
            Jwt<Header, Claims> jwt = Jwts.parser().setSigningKey(JWT_PASSWORD).parse(json.get("access_token"));
            email = (String) jwt.getBody().get("email");
            refreshToken = json.get("refresh_token");
        } catch (ExpiredJwtException e) {
            email = (String) e.getClaims().get("email");
            refreshToken = json.get("refresh_token");
        }

        if (email == null || refreshToken == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
        else {
            String finalRefreshToken = refreshToken;
            User user = userService.findByEmail(email);
            if (user.getTokens().stream().anyMatch(t -> t.getRefresh_token().equals(finalRefreshToken))) {
                tokenService.delete(user.getTokens().stream().filter(t -> t.getRefresh_token().equals(finalRefreshToken)).findFirst().get());
                Map<String, String> tokens = jwtService.createNewTokens(user.getUser_id(), email, user.getFirstName(), user.getLastName(), user.getProvider());
                Cookie cookie_access_token = new Cookie("access_token", tokens.get("access_token"));
                cookie_access_token.setHttpOnly(true);
                response.addCookie(cookie_access_token);
                Cookie cookie_refresh_token = new Cookie("refresh_token", tokens.get("refresh_token"));
                cookie_refresh_token.setHttpOnly(true);
                response.addCookie(cookie_refresh_token);


            } else {
                response.setStatus(HttpStatus.FORBIDDEN.value());

            }
        }

    }
*/
//@CookieValue(value = "refresh_token") String refresh_token_input
    @Operation(summary = "Выдает новые токены в куки")
    @PostMapping("/newToken")
    @ResponseBody
    @ApiModelProperty(
            value = "A JSON value representing a transaction. An example of the expected schema can be found down here. The fields marked with an * means that they are required.",
            example = "{foo: whatever, bar: whatever2}")
    public void newToken(@CookieValue(value = "access_token",required = false) String access_token_input, @CookieValue(value = "refresh_token",required = false) String refresh_token_input,HttpServletResponse response) {

  //      System.out.println("kek");

   //     return ResponseEntity.ok().body(access_token_input+" \n"+refresh_token_input);


        String email;
        String refreshToken;
        try {
            Jwt<Header, Claims> jwt = Jwts.parser().setSigningKey(JWT_PASSWORD).parse(access_token_input);
            email = (String) jwt.getBody().get("email");
            refreshToken = refresh_token_input;
        } catch (ExpiredJwtException e) {
            email = (String) e.getClaims().get("email");
            refreshToken = refresh_token_input;
        }

        if (email == null || refreshToken == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
        else {
            String finalRefreshToken = refreshToken;
            User user = userService.findByEmail(email);
            if (user.getTokens().stream().anyMatch(t -> t.getRefresh_token().equals(finalRefreshToken))) {
                tokenService.delete(user.getTokens().stream().filter(t -> t.getRefresh_token().equals(finalRefreshToken)).findFirst().get());
                Map<String, String> tokens = jwtService.createNewTokens(user.getUser_id(), email, user.getFirstName(), user.getLastName(), user.getProvider());
                Cookie cookie_access_token = new Cookie("access_token", tokens.get("access_token"));
                cookie_access_token.setHttpOnly(true);
                response.addCookie(cookie_access_token);
                Cookie cookie_refresh_token = new Cookie("refresh_token", tokens.get("refresh_token"));
                cookie_refresh_token.setHttpOnly(true);
                response.addCookie(cookie_refresh_token);


            } else {
                response.setStatus(HttpStatus.FORBIDDEN.value());

            }
        }
    }

}
