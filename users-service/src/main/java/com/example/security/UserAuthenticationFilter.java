package com.example.security;

import com.example.dto.GetUsersResponseDto;
import com.example.dto.LoginDto;
import com.example.properties.ConfigProperties;
import com.example.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
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

/**
 * << UserAuthenticationFilter 는 login 시에만 동작 >>
 * - attemptAuthentication 으로 인증 시도
 * - 사용자가 입력한 id,password 같은 인증 정보를 기반으로
 * - bean 으로 등록된 AuthenticationManager 을 통해 자동 으로 인증 시도(using UserDetailsService)
 * - successfulAuthentication 으로 인증 후 jwt 발급 using Authentication
 **/
@Slf4j
@Component
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private ConfigProperties configProperties;
    private UsersService usersService;

    /* AuthenticationManager 는 SecurityConfig 에서 Bean 으로 이미 등록됨 */
    public UserAuthenticationFilter(AuthenticationManager authenticationManager,
                                    UsersService usersService, ConfigProperties configProperties
    ) {
        super.setAuthenticationManager(authenticationManager);
        this.usersService = usersService;
        this.configProperties = configProperties;
        log.info("secret key : " + configProperties.getSecret());
    }

    /* 인증 시도, UserDetailsService 의 loadUserByUsername 메소드 호출 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            log.info("attemptAuthentication called");
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
                .setExpiration(new Date(System.currentTimeMillis() + configProperties.getExpiration()))
                .signWith(SignatureAlgorithm.HS256, configProperties.getSecret())
                .compact();

        response.addHeader("authorization", "Bearer " + token); // 생성한 jwt 을 반환
        response.addHeader("userId", userDto.getUserId());

        response.getWriter().write("Access Token 발급이 완료되었습니다.");
        return;
    }
}
