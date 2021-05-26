package ru.naumen.ectmauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.ectmauth.constants.AuthConstants;
import ru.naumen.ectmauth.dto.NewUserDto;
import ru.naumen.ectmauth.entity.Provider;
import ru.naumen.ectmauth.entity.User;
import ru.naumen.ectmauth.exception.AuthServiceException;
import ru.naumen.ectmauth.repository.RoleRepository;
import ru.naumen.ectmauth.repository.UserRepository;
import ru.naumen.ectmauth.utils.CryptoUtils;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void registerNewUser(NewUserDto newUserDto) {

        if (newUserDto.getEmail() == null || newUserDto.getPassword() == null) {
            throw new AuthServiceException("Email and password are required");
        }

        User user = new User();
        user.setFirstName(newUserDto.getFirstName());
        user.setLastName(newUserDto.getLastName());
        user.setEmail(newUserDto.getEmail());
        user.setPassword(CryptoUtils.sha256WithSalt(newUserDto.getPassword()));
        user.setRoles(roleRepository.findAllByNames(AuthConstants.DEFAULT_ROLE_NAMES));
        user.setProvider(Provider.local);

        userRepository.save(user);
    }
}
