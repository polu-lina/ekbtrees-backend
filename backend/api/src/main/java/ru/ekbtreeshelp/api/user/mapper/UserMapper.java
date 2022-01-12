package ru.ekbtreeshelp.api.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.ekbtreeshelp.api.user.dto.UpdateUserDto;
import ru.ekbtreeshelp.api.user.dto.UserDto;
import ru.ekbtreeshelp.core.entity.User;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    UserDto toDto(User user);

    void updateUserFromDto(UpdateUserDto updateUserDto, @MappingTarget User user);

    default User fromId(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    default Long toId(User user) {
        return user != null ? user.getId() : null;
    }
}
