package com.api.auth.infra.repository;

import com.api.auth.infra.outgoing.UserORM;
import com.api.auth.infra.outgoing.IAuthRepository;
import com.api.auth.domain.entity.Client;
import com.api.util.Response;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

@Repository
public class AuthImplemnet implements IAuthRepository {

    private static final Logger logger = LoggerFactory.getLogger(AuthImplemnet.class);
    private final AuthenticationManager authenticationManager;
    private final UserORM userORM;
    

    @Autowired
    public AuthImplemnet(UserORM userORM, AuthenticationManager authenticationManager) {
        this.userORM = userORM;
        this.authenticationManager = authenticationManager;
        
    }

    @Override
    public Response register(Client user) {
        try {
            Client client = userORM.save(user);
            logger.info("Registro exitoso, Cliente: " + client.getName());
            return new Response(
                    "Registro exitoso",
                    HttpStatus.OK.value(),
                    true,
                    client);
        } catch (Exception e) { 
            logger.error("Este es el ERROR  ", e.getMessage());
            return new Response(
                    "ERROR Register not found " + e.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),
                    false,
                    null);
        }
    }

    @Override
    public Response login(String email, String password) {
        try {
            Authentication authentication = this.authenticationManager(email, password);
            return new Response(
                    "login exitoso",
                    HttpStatus.OK.value(),
                    true,
                    authentication);
        } catch (BadCredentialsException e) {
            return new Response(
                    "login no exitoso",
                    HttpStatus.UNAUTHORIZED.value(),
                    false,
                    null);
        }

    }

    private Authentication authenticationManager(String email, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
    }

}
