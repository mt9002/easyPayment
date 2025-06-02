package com.api.confg;

import com.api.auth.infra.security.config.SecurityConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
@Profile("test")
public class NoSecurityConfig extends SecurityConfig{
    
    @Bean
    @Override
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        return http
                 
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest
                        -> authRequest
                                .anyRequest().permitAll()
                )
                
                .sessionManagement(sessionManager
                        -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build(); 
    }
}