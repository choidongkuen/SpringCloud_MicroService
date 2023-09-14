package com.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GatewayServiceController {

    @GetMapping("/first-service")
    public String firstService() {
        return "This is a first Service";
    }

    @GetMapping("/second-service")
    public String secondService() {
        return "This is a second Service";
    }
}
