package com.example.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@AllArgsConstructor
@Getter
@ConfigurationProperties(prefix = "application")
public class ConfigProperties {
    private String name;
    private String file;
    private String profile;
}
