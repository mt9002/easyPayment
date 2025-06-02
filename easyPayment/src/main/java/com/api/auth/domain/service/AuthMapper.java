package com.api.auth.domain.service;

import com.api.auth.app.dto.RegisterDTO;
import com.api.auth.domain.entity.Client;
import com.api.auth.domain.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AuthMapper{
    
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public AuthMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    public Client DtoToEntity(RegisterDTO registerRequestDTO) {
        Client client = new Client(
               registerRequestDTO.getName(),
               registerRequestDTO.getLastName(),
               registerRequestDTO.getEmail(),
               passwordEncoder.encode(registerRequestDTO.getPassword())      
        );
        client.setRole(Role.valueOf("USER"));
        return client;
    }

}