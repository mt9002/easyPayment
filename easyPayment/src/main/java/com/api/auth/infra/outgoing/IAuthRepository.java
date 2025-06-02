package com.api.auth.infra.outgoing;

import com.api.auth.domain.entity.Client;
import com.api.util.Response;

public interface IAuthRepository {

    public Response register(Client user);

    public Response login(String email, String password);
}
