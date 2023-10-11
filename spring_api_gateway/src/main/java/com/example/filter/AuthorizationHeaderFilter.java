package com.example.filter;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Objects;


/**
 * - 사용자 요청에 대한 인증 처리하는 필터
 * - 회원가입, 로그인을 제외한 모든 요청에 대해 jwt 검증 하는 필터
 **/
@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Value("${jwt.authorization-header}")
    private String JWT_PREFIX;

    /* Users-Service 에서 가지고 있는 개인키 Secret Key */
    @Value("${jwt.secret.key}")
    private String secretKey;
    public AuthorizationHeaderFilter() {
        super(Config.class);
    }
    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            /* /users/config/ -> Users-service 의 설정 정보를 조회하는 앤드포인트는 pass */
            if(request.getURI().equals("/users/config")){
                return chain.filter(exchange);
            }

            // 요청 헤더에 Authorization Header 포함 여부 체크
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
            }
            String authorizationHeader = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            String jwt = authorizationHeader.replace(JWT_PREFIX,""); // Bearer -> ""

            // 파싱한 jwt 유효성 검증
            if (!isJwtValid(jwt)) {
                return onError(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED);
            }

            // jwt 존재 하면 검증 완료시 다음 필터로 이동
            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange,
                               String err,
                               HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);
        // 응답 반환
        return response.setComplete();
    }

    private boolean isJwtValid(String jwt) {
        String userId = null;
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt);

            userId = claimsJws.getBody().getSubject();
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
