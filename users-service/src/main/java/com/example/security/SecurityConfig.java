package com.example.security;

import com.example.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UsersService usersService;
    private final Environment environment;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().antMatchers("/**")
                .permitAll()
                .and()
                .addFilter(getUserAuthenticationFilter());


        return http.build();
    }

    /* UserAuthenticationFilter */
    private UserAuthenticationFilter getUserAuthenticationFilter() throws Exception {
        UserAuthenticationFilter userAuthenticationFilter
                = new UserAuthenticationFilter(getAuthenticationManager(new AuthenticationConfiguration()),usersService,environment);
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
