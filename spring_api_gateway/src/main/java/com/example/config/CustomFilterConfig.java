package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilterConfig extends AbstractGatewayFilterFactory<CustomFilterConfig.Config> {
    public CustomFilterConfig() {
        super(Config.class);
    }


    public static class Config {
        // put the configuration properties
    }

    @Override
    public GatewayFilter apply(Config config) {

        // Custom Pre Filter
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            System.out.println("CUSTOM PRE filter: request id -> " + request.getId());

            // Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("CUSTOM POST filter: response code -> " + response.getStatusCode());
            }));
        });
    }
}
