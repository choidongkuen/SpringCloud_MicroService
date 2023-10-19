package com.example.security;

import com.example.properties.ConfigJwtProperties;
import com.example.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final UsersService usersService;
    private final ConfigJwtProperties configJwtProperties;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .antMatchers("/users/config");
    }

    /**
     * - Users-Service application 에 Spring actuator 적용하려고 한다.
     * - Spring Security 에 /actuator/** 대한 모든 요청을 허용
     * - jwt 검증이 필요한 요청은 Spring Cloud Gateway's AuthorizationHeaderFilter 을 거치며,
     * - jwt 생성 using 아이디, 비밀번호는 UserAuthenticationFilter 에서 발생 한다.
     **/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().antMatchers("/actuator/**",
                        "/users/login").permitAll()// Spring Actuator 모든 요청 인증 x
                .and()
                .addFilter(getUserAuthenticationFilter());
        return http.build();
    }

    /* UserAuthenticationFilter */
    private UserAuthenticationFilter getUserAuthenticationFilter() throws Exception {
        UserAuthenticationFilter userAuthenticationFilter
                = new UserAuthenticationFilter(getAuthenticationManager(new AuthenticationConfiguration()), usersService, configJwtProperties);
        userAuthenticationFilter.setAuthenticationManager(getAuthenticationManager(new AuthenticationConfiguration()));
        return userAuthenticationFilter;
    }

    /* AuthenticationManager */
    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
