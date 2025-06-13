package com.api.auth.app.service;

import com.api.auth.presentation.dto.RegisterDTO;
import com.api.auth.domain.entity.Role;
import com.api.auth.domain.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthMapper {

    public static User DtoToEntity(RegisterDTO registerRequestDTO, PasswordEncoder passwordEncoder) {

        String hash = passwordEncoder.encode(registerRequestDTO.getPassword());
        User user = new User(
                registerRequestDTO.getName(),
                registerRequestDTO.getLastName(),
                registerRequestDTO.getEmail(),
                hash
        );
        user.setRole(Role.valueOf("USER"));
        return user;
    }
}
