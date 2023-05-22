package ru.ekbtreeshelp.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.core.entity.RefreshToken;
import ru.ekbtreeshelp.core.entity.User;

import java.util.Set;

@Repository
@Transactional
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Modifying
    void deleteAllByUserId(Long userId);

    Set<RefreshToken> getAllByUserId(Long userId);

    @Modifying
    void deleteByTokenAndUser(String token, User user);
}
