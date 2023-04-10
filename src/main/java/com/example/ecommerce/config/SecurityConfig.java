package com.example.ecommerce.config;

import com.example.ecommerce.filter.JwtAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .cors().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(STATELESS)
            .and()
            .authorizeHttpRequests(auth ->
                    auth
                            .requestMatchers("/api/admin/**").hasRole("ADMIN")
                            .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
                            .requestMatchers("/api/store/**").hasRole("STORE")
                            .requestMatchers("/api/delivery-partner/**").hasRole("DELIVERY_PARTNER")
                            .requestMatchers("/api/auth/**").permitAll()
                            .anyRequest().permitAll()
            );

        http.addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
