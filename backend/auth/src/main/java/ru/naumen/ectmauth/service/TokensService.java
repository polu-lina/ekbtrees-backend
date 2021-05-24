package ru.naumen.ectmauth.service;

import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.ectmauth.dto.JwtDto;
import ru.naumen.ectmauth.entity.RefreshToken;
import ru.naumen.ectmauth.entity.User;
import ru.naumen.ectmauth.repository.RefreshTokenRepository;
import ru.naumen.ectmauth.repository.UserRepository;
import ru.naumen.ectmauth.utils.CryptoUtils;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TokensService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTService jwtService;

    public JwtDto getTokensByCredentials(String email, String password) throws InvalidCredentialsException {
        User user = userRepository.findByEmail(email).orElseThrow(this::invalidLoginOrPassword);

        if (!user.getPassword().equals(CryptoUtils.sha256WithSalt(password))) {
            throw invalidLoginOrPassword();
        }

        return generateNewTokens(user);
    }

    public JwtDto getTokensByRefreshToken(String refreshToken) throws InvalidCredentialsException {

        User user;
        try {
            user = jwtService.extractUser(refreshToken, true);
        } catch (Exception e) {
            throw invalidRefreshToken(e);
        }

        refreshTokenRepository.getAllByUserId(user.getId())
                .stream()
                .filter((token -> token.getToken().equals(refreshToken)))
                .findFirst()
                .orElseThrow(this::invalidRefreshToken);

        return generateNewTokens(user);
    }

    private JwtDto generateNewTokens(User user) {
        JwtDto jwtDto = jwtService.generateNewPair(user);

        refreshTokenRepository.deleteAllByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(jwtDto.getRefreshToken());
        refreshToken.setUser(user);

        refreshTokenRepository.save(refreshToken);

        return jwtDto;
    }

    public JwtDto getTokensByOAuth2(User user) {
        return generateNewTokens(user);
    }

    private InvalidCredentialsException invalidLoginOrPassword() {
        return new InvalidCredentialsException("Invalid login and/or password");
    }

    private InvalidCredentialsException invalidRefreshToken(Throwable cause) {
        return new InvalidCredentialsException(cause.getMessage(), cause);
    }

    private InvalidCredentialsException invalidRefreshToken() {
        return new InvalidCredentialsException("Token already revoked");
    }
}
