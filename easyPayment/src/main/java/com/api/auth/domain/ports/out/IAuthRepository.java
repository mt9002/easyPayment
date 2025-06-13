package com.api.auth.domain.ports.out;

import com.api.auth.domain.entity.User;
import com.api.auth.app.service.util.Response;

public interface IAuthRepository {

    public Response register(User user);
}
