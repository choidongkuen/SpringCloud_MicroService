package com.example.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@Setter
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public class ConfigProperties {
    private String application;
    private Long expiration;
    private String secret;
    private String type;
    private String profile;
}
