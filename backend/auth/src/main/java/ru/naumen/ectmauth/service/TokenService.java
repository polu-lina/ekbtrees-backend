package ru.naumen.ectmauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.ectmauth.entity.Token;
import ru.naumen.ectmauth.repository.TokenRepository;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public Token save(Token token) {
        return tokenRepository.save(token);
    }
    public void delete(Token token){ tokenRepository.delete(token);}

}
