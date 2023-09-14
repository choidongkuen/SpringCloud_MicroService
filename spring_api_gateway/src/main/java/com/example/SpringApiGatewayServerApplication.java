package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringApiGatewayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringApiGatewayServerApplication.class,args);
    }
}
