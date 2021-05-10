package ru.naumen.ectmauth.token;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.naumen.ectmauth.token.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
    Token save(Token token);
    void delete (Token token);
}
