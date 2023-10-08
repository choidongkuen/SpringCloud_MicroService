package com.example.security;

import com.example.dto.GetUsersResponseDto;
import com.example.dto.LoginDto;
import com.example.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * - attemptAuthentication 으로 인증 시도
 * - successfulAuthentication 으로 인증 후 jwt 발급 using Authentication
**/
@Slf4j
@Component
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UsersService usersService;
    private Environment environment;


    /* AuthenticationManager 는 SecurityConfig 에서 Bean 으로 이미 등록됨 */
    public UserAuthenticationFilter(AuthenticationManager authenticationManager,
                                    UsersService usersService, Environment environment
    ) {
        super.setAuthenticationManager(authenticationManager);
        this.usersService = usersService;
        this.environment = environment;
    }

    /* 인증 시도, UserDetailsService 의 loadUserByUsername 메소드 호출 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            // email, password 는 요청 파라미터로 요청 바디을 통해 전달
            // 요청 바디를 읽기 위해 HttpServletRequest.getInputStream()
            LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword(), new ArrayList<>());
            // authenticationManager 의 authenticate 메소드를 통해 인증 시도(providerManager -> AuthenticationProvider -> UserDetailsService -> UserDetails)
            return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* 인증 성공 `후` 처리 해야 할 부분 구현*/
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication called");
        String email = ((User) authResult.getPrincipal()).getUsername();
        GetUsersResponseDto userDto = this.usersService.getUserDetailsByEmail(email);

        String token = Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(Objects.requireNonNull(environment.getProperty("jwt.token.access-expiration"))))
                )
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("jwt.secret.key"))
                .compact();

        response.addHeader("authorization","Bear " + token); // 생성한 jwt 을 반환
        response.addHeader("userId",userDto.getUserId());
    }
}
