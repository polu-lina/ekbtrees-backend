package ru.naumen.ectmauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.ectmauth.entity.Token;
import ru.naumen.ectmauth.repository.TokenRepository;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TokenService {

    private final TokenRepository tokenRepository;

    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    public void delete(Token token) {
        tokenRepository.delete(token);
    }

}
