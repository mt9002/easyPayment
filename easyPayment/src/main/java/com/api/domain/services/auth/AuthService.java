package com.api.domain.services.auth;

import com.api.domain.interfaces.outgoing.IAuthRepository;
import com.api.app.dto.LoginDTO;
import com.api.app.dto.RegiterDTO;
import com.api.domain.entities.Client;
import com.api.domain.entities.Role;
import com.api.domain.interfaces.incoming.IAuthService;
import com.api.domain.services.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final PasswordEncoder passwordEncoder;
    private final IAuthRepository iAuthRepository;

    @Autowired
    public AuthService(IAuthRepository iAuthRepository, PasswordEncoder passwordEncoder) {
        this.iAuthRepository = iAuthRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Response register(RegiterDTO regiterDTO) {
        Client user = new Client(
                regiterDTO.getName(),
                regiterDTO.getLastName(),
                regiterDTO.getEmail(),
                passwordEncoder.encode(regiterDTO.getPassword()) // falta encriptar...
        );
        user.setRole(Role.valueOf("USER"));
        return iAuthRepository.register(user);
    }

    @Override
    public Response login(LoginDTO loginDTO) {

        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
        return iAuthRepository.login(email, password);
    }

}
