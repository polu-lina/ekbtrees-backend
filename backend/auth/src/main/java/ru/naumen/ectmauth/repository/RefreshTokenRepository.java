package ru.naumen.ectmauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.naumen.ectmauth.entity.RefreshToken;
import ru.naumen.ectmauth.entity.User;

import java.util.Set;

@Repository
@Transactional
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    void deleteAllByUser(User user);

    Set<RefreshToken> getAllByUserId(Long userId);
}
