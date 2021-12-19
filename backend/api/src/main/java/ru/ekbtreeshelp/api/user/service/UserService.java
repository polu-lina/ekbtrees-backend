package ru.ekbtreeshelp.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.api.user.mapper.UserMapper;
import ru.ekbtreeshelp.api.user.dto.UpdateUserDto;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.UserRepository;
import ru.ekbtreeshelp.core.utils.CryptoUtils;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CryptoUtils cryptoUtils;

    public User getById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional
    public void update(Long id, UpdateUserDto updateUserDto) {
        userMapper.updateUserFromDto(updateUserDto, userRepository.getOne(id));
    }

    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(cryptoUtils.sha256WithSalt(newPassword));
        userRepository.save(user);
    }
}
