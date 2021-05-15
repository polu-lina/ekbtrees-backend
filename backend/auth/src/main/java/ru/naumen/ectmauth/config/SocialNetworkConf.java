package ru.naumen.ectmauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "social-network")
public class SocialNetworkConf {
    private String vkSecretKey;
    private String fbSecretKey;
}
