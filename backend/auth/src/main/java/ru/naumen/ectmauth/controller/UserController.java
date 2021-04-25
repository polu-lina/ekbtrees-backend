package ru.naumen.ectmauth.controller;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

import javax.servlet.ServletException;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.naumen.ectmauth.HashingAssignment;
import ru.naumen.ectmauth.user.Role;
import ru.naumen.ectmauth.user.User;
import ru.naumen.ectmauth.user.UserServiceImpl;

import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    private static final String JWT_PASSWORD = "bm5n3SkxCX4kKRy4";
    private static final List<String> validRefreshTokens = new ArrayList<>();


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) throws ServletException, NoSuchAlgorithmException {

        User userTemp = userService.findByEmail(user.getEmail());
        if (userTemp != null) {
            throw new ServletException("This email are already used");
        }
        String hashedPassword = HashingAssignment.getHash(user.getPassword(), "SHA-256");
        User hashedUser = user;
        hashedUser.setPassword(hashedPassword);

        return userService.save(hashedUser);
    }

    @PostMapping("/login")
    @ResponseBody
    public void login(@RequestBody(required = false) Map<String, String> json, HttpServletResponse response) throws ServletException, NoSuchAlgorithmException {

        String email = json.get("email");
        String password = json.get("password");
        if (email == null || password == null) {
            throw new ServletException("Please fill in username and password");
        }

        User user = userService.findByEmail(email);

        if (user == null) {
            throw new ServletException("User email not found.");
        }

        String pwd = user.getPassword();

        if (!HashingAssignment.getHash(password, "SHA-256").equalsIgnoreCase(pwd)) {
            throw new ServletException("Invalid login. Please check your name and password.");
        }

        Map<String, String> tokens = createNewTokens(email);
        Cookie cookie_access_token = new Cookie("access_token", tokens.get("access_token"));
        cookie_access_token.setHttpOnly(true);
        response.addCookie(cookie_access_token);
        Cookie cookie_refresh_token = new Cookie("refresh_token", tokens.get("refresh_token"));
        cookie_refresh_token.setHttpOnly(true);
        response.addCookie(cookie_refresh_token);

    }

    @PostMapping("/newToken")
    @ResponseBody
    public void newToken(@RequestBody(required = false) Map<String, String> json, HttpServletResponse response) {
        if (json == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());

        }

        String user;
        String refreshToken;
        try {
            Jwt<Header, Claims> jwt = Jwts.parser().setSigningKey(JWT_PASSWORD).parse(json.get("access_token"));
            user = (String) jwt.getBody().get("email");
            refreshToken = json.get("refresh_token");
        } catch (ExpiredJwtException e) {
            user = (String) e.getClaims().get("email");
            refreshToken = json.get("refresh_token");
        }

        if (user == null || refreshToken == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        } else if (validRefreshTokens.contains(refreshToken)) {
            validRefreshTokens.remove(refreshToken);
            Map<String, String> tokens = createNewTokens(user);
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

    private Map<String, String> createNewTokens(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("admin", "false");
        claims.put("email", email);
        String token = getSecretToken(email);//jwt builder
        Map<String, String> tokenJson = new HashMap<>();
        String refreshToken = RandomStringUtils.randomAlphabetic(20);
        validRefreshTokens.add(refreshToken);
        tokenJson.put("access_token", token);
        tokenJson.put("refresh_token", refreshToken);
        return tokenJson;
    }

    private String getSecretToken(String email) {
        return Jwts.builder()
                .setIssuer("Auth Server Ekb Trees")
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(Date.from(Instant.now().plusSeconds(100000)))
                .claim("email", email)
                .claim("Role", userService.findByEmail(email).getRoles().stream().map(Role::getName).toArray(String[]::new))
                .signWith(SignatureAlgorithm.HS256, JWT_PASSWORD).compact();
    }


}
