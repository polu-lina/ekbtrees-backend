package ru.naumen.ectmauth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.naumen.ectmauth.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User save(User user);

    User findByEmail(String email);


    @Query("SELECT u FROM User u WHERE u.vk_id = :vk_id")
    public User findByVk_id(@Param("vk_id") String vk_id);

    @Query("SELECT u FROM User u WHERE u.fb_id = :fb_id")
    public User findByFb_id(@Param("fb_id") String fb_id);

}
