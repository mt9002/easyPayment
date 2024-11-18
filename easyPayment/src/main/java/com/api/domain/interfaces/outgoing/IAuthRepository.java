package com.api.domain.interfaces.outgoing;

import com.api.domain.entities.Client;
import com.api.domain.services.util.Response;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthRepository {

    public Response register(Client user);

    public Response login(String email, String password);

    public Response<UserDetails> findByEmail(String email);
}
