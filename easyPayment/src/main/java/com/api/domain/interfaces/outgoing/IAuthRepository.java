package com.api.domain.interfaces.outgoing;

import com.api.domain.entities.Client;
import com.api.domain.services.util.Response;

public interface IAuthRepository {

    public Response register(Client user);

    public Response login(String email, String password);
}
