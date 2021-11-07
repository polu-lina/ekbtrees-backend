package ru.ekbtreeshelp.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "superuser")
public class SuperUserConfig {
    String password;
}
