package ru.naumen.ectmauth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User findByVk_id(Integer vk_id){
        return userRepository.findByVk_id(vk_id);
    }
    public Optional<User> findById(Long id){return userRepository.findById(id);}
}
