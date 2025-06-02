package com.api.auth.infra.outgoing;

import com.api.auth.domain.entity.Client;
import com.api.util.Response;

public interface IUserRepository {
    Response<Client> getById(Long id);
}
