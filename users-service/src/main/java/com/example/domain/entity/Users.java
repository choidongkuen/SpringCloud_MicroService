package com.example.domain.entity;

import com.example.constant.RoleType;
import com.example.dto.GetUsersResponseDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, length=50, unique = true)
    private String email;

    @Column(nullable = false, length=50)
    private String name;

    @Column(nullable = false, unique = true)
    private String encryptedPassword;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public GetUsersResponseDto toGetUsersResponseEntity() {
        return GetUsersResponseDto.builder()
                .name(name)
                .email(email)
                .userId(userId)
                .build();
    }
}
