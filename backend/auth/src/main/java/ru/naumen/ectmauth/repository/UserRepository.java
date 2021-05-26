package ru.naumen.ectmauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.naumen.ectmauth.entity.User;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByVkId(Long vkId);

    User findByFbId(Long fbId);

}
