package com.example.security;

import com.example.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /* 인증 시도 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            // email, password 는 요청 파라미터로 요청 바디을 통해 전달
            // 요청 바디를 읽기 위해 HttpServletRequest.getInputStream()
            LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword(), new ArrayList<>());
            // authenticationManager 의 authenticate 메소드를 통해 인증 시도(providerManager -> AuthenticationProvider -> UserDetailsService -> UserDetails)
            return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* 인증 성공 */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
