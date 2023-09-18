package com.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GatewayServiceController {

    @GetMapping("/first-service")
    public String firstService(
            @RequestHeader("first-request") String header){
        System.out.println("first-service-header : " + header);
        return "This is a first Service";
    }

    @GetMapping("/second-service")
    public String secondService(
            @RequestHeader("second-request") String header) {

        System.out.println("second-service-header : " + header);
        return "This is a second Service";
    }
}
