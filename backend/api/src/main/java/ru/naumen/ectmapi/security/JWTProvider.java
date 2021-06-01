package ru.naumen.ectmapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.ectmapi.config.JWTConfig;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JWTProvider {

    private final JWTConfig config;

    public JWTUserDetails getUserDetailsFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(config.getAccessTokenSecret()).parseClaimsJws(token);
        return JWTUserDetails.fromParsedTokenPayload(claimsJws.getBody());
    }
}
