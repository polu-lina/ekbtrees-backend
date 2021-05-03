package ru.naumen.ectmauth.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.ectmauth.token.Token;
import ru.naumen.ectmauth.token.TokenRepository;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public Token save(Token token) {
        return tokenRepository.save(token);
    }
    public void delete(Token token){ tokenRepository.delete(token);}

}
