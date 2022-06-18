package ru.ekbtreeshelp.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.api.user.mapper.UserMapper;
import ru.ekbtreeshelp.api.user.dto.UpdateUserDto;
import ru.ekbtreeshelp.api.user.dto.UpdateUserPasswordDto;
import ru.ekbtreeshelp.api.user.dto.UserDto;
import ru.ekbtreeshelp.api.security.service.SecurityService;
import ru.ekbtreeshelp.api.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Tag(name = "Операции с пользователями")
@Validated
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final SecurityService securityService;

    @GetMapping("/{id}")
    @Operation(summary = "Предоставляет информацию о пользователе по идентификатору")
    @PreAuthorize("permitAll()")
    public UserDto getById(@PathVariable Long id) {
        return userMapper.toDto(userService.getById(id));
    }

    @SecurityRequirement(name = "jwt")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Предоставляет информацию о текущем пользователе")
    public UserDto getSelf() {
        Long currentUserId = securityService.getCurrentUserId();
        return userMapper.toDto(userService.getById(currentUserId));
    }

    @SecurityRequirement(name = "jwt")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(@Roles.SUPERUSER, @Roles.MODERATOR) or " +
            "hasPermission(#id, @Domains.USER, @Permissions.EDIT)")
    @Operation(summary = "Редактирует пользователя")
    public void updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        userService.update(id, updateUserDto);
    }

    @SecurityRequirement(name = "jwt")
    @PutMapping("/updatePassword")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Редактирует пароль текущего пользователя")
    public void updatePassword(@RequestBody @Valid UpdateUserPasswordDto updateUserPasswordDto) {
        userService.updatePassword(securityService.getCurrentUser(), updateUserPasswordDto.getNewPassword());
    }

    @SecurityRequirement(name = "jwt")
    @GetMapping("/getAll/{page}/{size}")
    @PreAuthorize("hasAnyAuthority(@Roles.SUPERUSER, @Roles.MODERATOR)")
    @Operation(summary = "Получение списка всех пользователей")
    public List<UserDto> listAll(@PathVariable @Min(0) Integer page,
                                 @PathVariable @Min(1) @Max(100) Integer size) {
        return userService.listAll(page, size).stream()
                .map(userMapper::toDto)
                .toList();
    }
}
