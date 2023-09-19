package com.example.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "greeting")
@Setter @Getter
@ConstructorBinding
public class MessageProperties {
    private String message;
}
