package ru.ekbtreeshelp.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ekbtreeshelp.api.converter.UserConverter;
import ru.ekbtreeshelp.api.dto.UserDto;
import ru.ekbtreeshelp.api.service.SecurityService;
import ru.ekbtreeshelp.api.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final SecurityService securityService;

    @GetMapping("/{id}")
    @Operation(summary = "Предоставляет информацию о пользователе по идентификатору")
    public UserDto getById(@PathVariable Long id) {
        return userConverter.toDto(userService.getById(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Предоставляет информацию о текущем пользователе")
    public UserDto getSelf() {
        Long currentUserId = securityService.getCurrentUserId();
        return userConverter.toDto(userService.getById(currentUserId));
    }
}
