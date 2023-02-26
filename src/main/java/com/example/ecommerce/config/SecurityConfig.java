package com.example.ecommerce.config;

import com.example.ecommerce.filter.JwtAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //TODO: authorize the endpoints

        http
            .cors().disable()
            .csrf().disable()
            .authorizeHttpRequests(auth ->
                    auth.anyRequest().permitAll()
            );

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
