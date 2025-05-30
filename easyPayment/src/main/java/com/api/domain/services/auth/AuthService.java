package com.api.domain.services.auth;

import com.api.domain.interfaces.outgoing.IAuthRepository;
import com.api.app.dto.LoginDTO;
import com.api.app.dto.RegisterDTO;
import com.api.domain.entities.Client;
import com.api.domain.interfaces.incoming.IAuthService;
import com.api.domain.interfaces.outgoing.IJWT;
import com.api.domain.services.mapper.AuthMapper;
import com.api.domain.services.util.Response;
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
                System.out.println(user);
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
