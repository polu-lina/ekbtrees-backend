package ru.naumen.ectmapi.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.naumen.ectmapi.entity.User;

@Mapper
public interface UserRepository {
    User findByEmail(String email);
}
