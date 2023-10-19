package com.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/second-service")
@RestController
public class SecondServiceController {
    @GetMapping("/header")
    public String secondService(
            @RequestHeader("second-request") String header) {
        System.out.println("second-service-header : " + header);
        return "This is a second service";
    }

    @GetMapping("/check")
    public String secondServiceCheck() {
        return "Hi This is a second-service";
    }

}
