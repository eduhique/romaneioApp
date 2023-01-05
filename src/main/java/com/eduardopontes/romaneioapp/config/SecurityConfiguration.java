package com.eduardopontes.romaneioapp.config;

import com.eduardopontes.romaneioapp.security.jwt.JWTAuthFilter;
import com.eduardopontes.romaneioapp.security.jwt.JWTService;
import com.eduardopontes.romaneioapp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfiguration {

    private final UserService userService;

    private final JWTService jwtService;

    public SecurityConfiguration(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JWTAuthFilter(jwtService, userService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(
                        auth -> auth
                                .antMatchers(HttpMethod.POST, "/users")
                                .permitAll()
                                .antMatchers("/auth/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
