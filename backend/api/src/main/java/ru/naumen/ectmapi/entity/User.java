package ru.naumen.ectmapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends Entity<Long> {
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}
