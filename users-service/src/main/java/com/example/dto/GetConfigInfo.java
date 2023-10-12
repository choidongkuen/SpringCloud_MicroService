package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetConfigInfo {
    private String application;
    private String secretKey;
    private Long expiration;
    private String type;
    private String profile;
}
