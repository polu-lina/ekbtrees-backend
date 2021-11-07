package ru.ekbtreeshelp.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.auth.config.SuperUserConfig;
import ru.ekbtreeshelp.auth.dto.NewUserDto;
import ru.ekbtreeshelp.core.entity.Role;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.RoleRepository;

import java.util.List;
import java.util.Set;

import static ru.ekbtreeshelp.auth.constants.DefaultRoles.*;
import static ru.ekbtreeshelp.auth.constants.SuperUserConstants.EMAIL;

@Component
@RequiredArgsConstructor
public class StartupService {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final SuperUserConfig superUserConfig;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createDefaultRoles();
        createSuperUser();
    }

    protected void createDefaultRoles() {
        Set.of(SUPERUSER, MODERATOR, VOLUNTEER).forEach(roleName -> {
            if (!roleRepository.existsByName(roleName)) {
                roleRepository.save(new Role(roleName));
            }
        });
    }

    @Transactional
    protected void createSuperUser() {
        if (!userService.isUserExists(EMAIL)) {
            User superUser = userService.registerNewUser(
                    new NewUserDto("super",
                            "user",
                            EMAIL,
                            superUserConfig.getPassword()
                    )
            );

            superUser.setRoles(roleRepository.findAllByNames(List.of(SUPERUSER)));
            userService.update(superUser);
        }
    }
}
