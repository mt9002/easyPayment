package com.api.infra.repositories;

import com.api.domain.entities.Client;
import com.api.domain.interfaces.outgoing.IAuthRepository;
import com.api.domain.interfaces.outgoing.jpaORM.UserORM;
import com.api.domain.services.util.Response;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class AuthImplemnet implements IAuthRepository {

    private static final Logger logger = LoggerFactory.getLogger(AuthImplemnet.class);
    private final UserORM userORM;

    @Autowired
    public AuthImplemnet(UserORM userORM) {
        this.userORM = userORM;
    }

    @Override
    public Response register(Client user) {
        try {
            Client client = userORM.save(user);
            logger.info("Registro exitoso, Cliente: ", user.getUsername());
            return new Response(
                    "Registro exitoso",
                    200,
                    true,
                    client);
        } catch (Exception e) { 
            logger.error("Este es el ERROR CARE-VERGA ", e.getMessage());
            return new Response(
                    "ESTE ES EL ERROR CARE-VERGA: " + e.getMessage(),
                    400,
                    false,
                    null);
        }
    }

    @Override
    public Response login(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<UserDetails> findByEmail(String email) {
        try {
            Optional<Client> client = userORM.findByEmail(email);
            logger.info("User encontrado. Username: "+ client.get().getEmail());
            return new Response(
                    "",
                    200,
                    true,
                    client.get());
        } catch (Exception e) {
            return new Response(
                    e.getMessage(),
                    400,
                    false,
                    null);
        }
    }

}
