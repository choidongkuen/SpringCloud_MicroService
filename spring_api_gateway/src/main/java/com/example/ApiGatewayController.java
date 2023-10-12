package com.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiGatewayController {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.type}")
    private String type;

    @Value("${jwt.profile}")
    private String profile;

    @GetMapping("/api/gateway/test")
    public String test() {
        log.info("secret : {}", secret);
        log.info("expiration : {}", expiration);
        log.info("type : {}", type);
        log.info("profile : {}", profile);

        return secret + profile;
    }
}
