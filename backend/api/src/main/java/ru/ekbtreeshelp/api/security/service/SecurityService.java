package ru.ekbtreeshelp.api.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.UserRepository;
import ru.ekbtreeshelp.api.security.JWTUserDetails;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        return userRepository.findById(getCurrentUserId()).orElseThrow();
    }

    public Authentication getCurrentAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean currentUserHasAnyRole(String... roles) {
        var currentRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return Arrays.stream(roles).anyMatch(currentRoles::contains);
    }

    public Long getCurrentUserId() {
        Authentication authentication = getCurrentAuth();
        if (authentication == null) {
            throw new IllegalStateException("There is no current authentication!");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof JWTUserDetails)) {
            throw new IllegalArgumentException("Unknown authentication, can't extract user id!");
        }
        return ((JWTUserDetails) principal).getId();
    }

}
