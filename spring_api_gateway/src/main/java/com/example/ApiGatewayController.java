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
    private final GetConfigInfo getConfigInfo;

    @GetMapping("/api/config")
    public GetConfigInfo test() {
        return this.getConfigInfo;
    }
}
