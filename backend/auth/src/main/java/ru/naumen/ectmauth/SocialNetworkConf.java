package ru.naumen.ectmauth;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "socialNetwork")
public class SocialNetworkConf {
    private String VkSecretKey;
}
