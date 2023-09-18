package com.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/first-service")
@RestController
public class FirstServiceController {

    @GetMapping("/header")
    public String firstService(
            @RequestHeader("first-request") String header){
        System.out.println("first-service-header : " + header);
        return "This is a first service";
    }

    @GetMapping("/check")
    public String firstServiceCheck() {
        return "Hi This is a first-service";
    }
}
