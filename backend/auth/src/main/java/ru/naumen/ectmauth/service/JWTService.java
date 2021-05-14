package ru.naumen.ectmauth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.ectmauth.entity.Provider;
import ru.naumen.ectmauth.entity.Role;
import ru.naumen.ectmauth.entity.Token;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private TokenService tokenService;
    private static final String JWT_PASSWORD = "bm5n3SkxCX4kKRy4";

    public Map<String, String> createNewTokensWithSocialNetwork(Long id, String email, String first_name, String last_name, Provider provider, String access_token_vk_or_fb){
        Map<String, Object> claims = new HashMap<>();
        claims.put("admin", "false");
        claims.put("email", email);
        String accessToken = getSecretToken(id,email,first_name,last_name, provider);//jwt builder
        Map<String, String> tokenJson = new HashMap<>();
        String refreshToken = RandomStringUtils.randomAlphabetic(20);
        Token t=new Token();
        t.setRefresh_token(refreshToken);
        t.setUser(userService.findById(id));
        t.setAccess_token(accessToken);
        if (provider == Provider.VK) {
            t.setAccess_token_VK(access_token_vk_or_fb);
        } else if (provider == Provider.FACEBOOK) {
            t.setAccess_token_FB(access_token_vk_or_fb);
        }

        tokenService.save(t);
        tokenJson.put("access_token", accessToken);
        tokenJson.put("refresh_token", refreshToken);
        return tokenJson;
    }

    public Map<String, String> createNewTokens(Long id, String email, String first_name, String last_name, Provider provider) {
        return createNewTokensWithSocialNetwork(id,email,first_name,last_name,provider,null);
    }

    private String getSecretToken( Long id,String email, String first_name, String last_name, Provider provider) {
        return Jwts.builder()
                .setIssuer("Auth Server Ekb Trees")
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(Date.from(Instant.now().plusSeconds(100000)))
                .claim("id",id)
                .claim("email", email)
                .claim("first_name",first_name)
                .claim("last_name",last_name)
                .claim("Role", userService.findById(id).get().getRoles().stream().map(Role::getName).toArray(String[]::new))
                .signWith(SignatureAlgorithm.HS256, JWT_PASSWORD).compact();
    }
}
