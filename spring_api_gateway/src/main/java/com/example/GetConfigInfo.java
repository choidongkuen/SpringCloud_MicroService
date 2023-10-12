package com.example;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ConfigurationProperties(prefix = "jwt")
@AllArgsConstructor
public class GetConfigInfo {
    private String application;
    private Long expiration;
    private String secret;
    private String type;
    private String profile;
}
