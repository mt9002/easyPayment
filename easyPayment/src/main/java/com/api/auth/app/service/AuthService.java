package com.api.auth.app.service;

import com.api.auth.app.service.util.ValidateDTO;
import com.api.auth.presentation.dto.*;
import com.api.auth.domain.entity.User;
import com.api.auth.domain.ports.in.IAuthService;
import com.api.auth.domain.ports.out.IUserRepository;
import com.api.auth.infra.presistence.entityJpa.Client;
import com.api.auth.infra.security.IJWT;
import com.api.auth.app.service.util.Response;
import com.api.auth.app.service.util.ResultState;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService implements IAuthService {

    private final IUserRepository userRepository;
    private final IJWT jwt;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticateUser;

    public AuthService(
            IUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            IJWT jwt,
            AuthenticationManager authenticateUser) {

        this.jwt = jwt;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticateUser = authenticateUser;
    }

    @Override
    public Response register(RegisterDTO registerDTO) {
        try {
            ValidateDTO.validateRegisterDTO(registerDTO); // validacion de datos

            User dtoToUser = AuthMapper.DtoToEntity(registerDTO, passwordEncoder);

            if (userRepository.getByIdEmail(dtoToUser.getEmail()).isEmpty()) {
                User user = userRepository.register(dtoToUser);
                return Response.success("Registro exitoso", user);
            }

            return Response.failure("Ya existe usuario con email " + dtoToUser.getEmail(), ResultState.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return Response.failure("campos vacios", e, ResultState.VALIDATION_ERROR);

        } catch (Exception e) {
            return Response.failure("ERROR Register not found ", e.getMessage(), ResultState.FAILURE);
        }
    }

    @Override
    public Response login(LoginDTO loginDTO) {

        try {
            String email = loginDTO.getEmail();
            String password = loginDTO.getPassword();
            Authentication authentication = this.authenticationManager(email, password);
            Client client = (Client) authentication.getPrincipal();
            String token = jwt.getToken(client);

            return Response.success("login exitoso", token);
        } catch (BadCredentialsException e) {
            return Response.failure("login no exitoso", e.getMessage(), ResultState.UNAUTHORIZED);
        }
    }

    private Authentication authenticationManager(String email, String password) {
        return authenticateUser.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
    }
}
