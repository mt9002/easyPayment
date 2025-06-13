package com.api.auth.infra.config;

import com.api.auth.app.service.AuthService;
import com.api.auth.app.service.UserService;
import com.api.auth.domain.ports.in.IAuthService;
import com.api.auth.domain.ports.in.IUserService;
import com.api.auth.domain.ports.out.IUserRepository;
import com.api.auth.infra.security.IJWT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

    @Bean
    public IAuthService authService(IUserRepository iAuthRepository, PasswordEncoder passwordEndonde,
            AuthenticationManager authenticationManager, IJWT jwt) {
        return new AuthService(iAuthRepository,passwordEndonde, jwt, authenticationManager);
    }
    
    @Bean 
    public IUserService userService(IUserRepository iUserRepository){
        return new UserService(iUserRepository);
    }
}
