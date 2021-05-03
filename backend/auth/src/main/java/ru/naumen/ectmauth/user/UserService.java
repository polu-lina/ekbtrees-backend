package ru.naumen.ectmauth.user;

import java.util.Optional;

public interface UserService {
    User save(User user);
    User findByEmail(String email);
    User findByVk_id(Integer vk_id);
    Optional<User> findById(Long id);


}
