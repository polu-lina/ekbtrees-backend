package ru.naumen.ectmauth.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User save(User user);

    User findByEmail(String email);


    @Query("SELECT u FROM User u WHERE u.vk_id = :vk_id")
    public User findByVk_id(@Param("vk_id") Integer vk_id);



}
