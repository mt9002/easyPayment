package com.api.domain.services.mapper;

import com.api.app.dto.RegiterDTO;
import com.api.domain.entities.Client;
import com.api.domain.entities.Role;
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
    
    public Client DtoToEntity(RegiterDTO registerRequestDTO) {
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