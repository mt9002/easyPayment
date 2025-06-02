package com.api.auth.domain.service;

import com.api.auth.infra.outgoing.IAuthRepository;
import com.api.auth.app.dto.LoginDTO;
import com.api.auth.app.dto.RegisterDTO;
import com.api.auth.domain.entity.Client;
import com.api.auth.infra.security.IJWT;
import com.api.auth.domain.incoming.IAuthService;
import com.api.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final IAuthRepository iAuthRepository;
    private final IJWT jwt;
    private final AuthMapper authMapper;

    @Autowired
    public AuthService(IAuthRepository iAuthRepository, IJWT jwt, AuthMapper authMapper) {
        this.iAuthRepository = iAuthRepository;
        this.jwt = jwt;
        this.authMapper = authMapper;
    }

    @Override
    public Response register(RegisterDTO regiterDTO) {
        Client user = authMapper.DtoToEntity(regiterDTO);
        return iAuthRepository.register(user);
    }

    @Override
    public Response login(LoginDTO loginDTO) {

            String email = loginDTO.getEmail();
            String password = loginDTO.getPassword();
            
            Response<Authentication> response = iAuthRepository.login(email, password);

            if (!response.isSuccess()) {
                return new Response(
                        response.getMessage(),
                        response.getStatus(),
                        false,
                        null
                );
            } else {
                Client user = (Client) response.getData().getPrincipal();
                String token = jwt.getToken(user);
                return new Response(
                        response.getMessage(),
                        response.getStatus(),
                        response.isSuccess(),
                        token
                );
            }

           
        }
    }
