package ru.naumen.ectmauth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.naumen.ectmauth.entity.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
    Token save(Token token);

    void delete(Token token);
}
