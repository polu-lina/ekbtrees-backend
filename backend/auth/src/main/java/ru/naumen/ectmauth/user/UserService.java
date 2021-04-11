package ru.naumen.ectmauth.user;

public interface UserService {
    User save(User user);
    User findByEmail(String email);
}
