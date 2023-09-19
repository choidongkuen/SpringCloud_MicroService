package com.example.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ROLE_ADMIN("관리자"), ROLE_USER("일반 유저");
    private final String role;
}
