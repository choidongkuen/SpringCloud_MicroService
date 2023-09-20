package com.example.controller;

import com.example.dto.UsersCreateRequestDto;
import com.example.properties.MessageProperties;
import com.example.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users-service/users")
@RestController
public class UsersController {

    private final MessageProperties messageProperties;
    private final UsersService usersService;
    private final Environment environment;

    // @ConfigurationProperties 대신 @Value 사용 가능(Relaxed Binding x)
    @Value("${greeting.message}")
    private String message;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s",
                environment.getProperty("local.server.port"));
    }

    @PostMapping
    public ResponseEntity<Long> createUser(
            @RequestBody @Valid UsersCreateRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.usersService.createUser(request));
    }
}
