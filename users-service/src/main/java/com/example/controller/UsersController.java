package com.example.controller;

import com.example.dto.UsersCreateRequestDto;
import com.example.properties.MessageProperties;
import com.example.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class UsersController {

    private final MessageProperties messageProperties;
    private final UsersService usersService;

    // @ConfigurationProperties 대신 @Value 사용 가능(Relaxed Binding x)
    @Value("${greeting.message}")
    private String message;

    @GetMapping("/health-check")
    public String status() {
        return messageProperties.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<Long> createUser(UsersCreateRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.usersService.createUser(request));
    }
}
