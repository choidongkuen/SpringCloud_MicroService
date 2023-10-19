package com.example.controller;

import com.example.dto.CreateUsersRequestDto;
import com.example.dto.GetConfigInfo;
import com.example.dto.GetUsersResponseDto;
import com.example.properties.ConfigJwtProperties;
import com.example.properties.ConfigProperties;
import com.example.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UsersController {
    private final UsersService usersService;
    private final Environment environment;
    private final ConfigJwtProperties configJwtProperties;
    private final ConfigProperties configProperties;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s",
                environment.getProperty("local.server.port"));
    }

    // 유저 생성
    // POST : http://localhost:8080/users
    @PostMapping
    public ResponseEntity<Long> createUser(
            @RequestBody @Valid CreateUsersRequestDto request) {
        request.setPassword(this.bcryptPasswordEncoder.encode(request.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.usersService.createUser(request));
    }

    // 모든 유저 조회
    // GET : http://localhost:8080/users
    @GetMapping
    public ResponseEntity<List<GetUsersResponseDto>> getAllUsers(
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.OK).body(this.usersService.getAllUsers());
    }

    // id 유저 조회
    // GET : http://localhost:8080/users/{userID}
    @GetMapping("/{userId}")
    public ResponseEntity<GetUsersResponseDto> getUser(
            @PathVariable String userId
    ) {
        String a = "62682241-b8e9-4d22-9d2b-7dd663d38ac1";
        System.out.println((a.equals("62682241-b8e9-4d22-9d2b-7dd663d38ac1")));
        return ResponseEntity.status(HttpStatus.OK).body(this.usersService.getUserByUserId(userId));
    }

    // 설정정보를 조회하는 컨트롤러
    @GetMapping("/config")
    public ResponseEntity<?> config() throws IOException {
        log.info("profile activated : " + Arrays.toString(environment.getActiveProfiles()));
        log.info("secret key:" + this.configJwtProperties.getSecret());
        log.info("profile : " + this.configJwtProperties.getProfile());
        GetConfigInfo info = GetConfigInfo.builder()
                .application(configJwtProperties.getApplication())
                .secretKey(this.configJwtProperties.getSecret())
                .expiration(this.configJwtProperties.getExpiration())
                .type(this.configJwtProperties.getType())
                .profile(this.configJwtProperties.getProfile())
                .build();
        return ResponseEntity.ok().body(info);
    }
}