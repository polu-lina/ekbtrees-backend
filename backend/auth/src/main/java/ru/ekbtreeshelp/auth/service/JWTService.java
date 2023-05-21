package ru.ekbtreeshelp.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.auth.config.JWTConfigAuth;
import ru.ekbtreeshelp.auth.dto.JwtDto;
import ru.ekbtreeshelp.core.entity.Role;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.UserRepository;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JWTService {

    private static final String ISSUER = "EKBTrees Auth Service";

    private final JWTConfigAuth jwtConfigAuth;
    private final UserRepository userRepository;

    public JwtDto generateNewPair(User user) {
        Instant now = Instant.now();
        String accessToken = Jwts.builder()
                .setIssuer(ISSUER)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(jwtConfigAuth.getAccessTokenLifespan())))
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .signWith(SignatureAlgorithm.HS512, jwtConfigAuth.getAccessTokenSecret())
                .compact();

        String refreshToken = Jwts.builder()
                .setIssuer(ISSUER)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(jwtConfigAuth.getRefreshTokenLifespan())))
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .signWith(SignatureAlgorithm.HS512, jwtConfigAuth.getRefreshTokenSecret())
                .compact();

        return new JwtDto(accessToken, refreshToken);
    }

    public User extractUser(String token, boolean isRefreshToken) {
        String signingKey;
        if (isRefreshToken) {
            signingKey = jwtConfigAuth.getRefreshTokenSecret();
        } else {
            signingKey = jwtConfigAuth.getAccessTokenSecret();
        }
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        Long userId = Long.valueOf(String.valueOf(claims.get("id")));
        return userRepository.getOne(userId);
    }
}
