package com.example.dto;

import com.example.constant.RoleType;
import com.example.domain.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateUsersRequestDto {
    @Email(message = "This is not an email format")
    @NotBlank(message = "Email Cannot be null")
    private String email;

    @NotBlank(message = "Name Cannot be null")
    @Size(min = 2, message = "Name Cannot be less than two characters")
    private String name;

    @NotBlank(message = "Password Cannot be null")
    @Size(min = 8, message = "Password Cannot ne less than two characters")
    private String password;

    public Users toEntity() {
        return Users.builder()
                .email(email)
                .name(name)
                .role(RoleType.ROLE_USER)
                .encryptedPassword(password)
                .userId(UUID.randomUUID().toString())
                .build();
    }
}
