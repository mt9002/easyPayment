package com.api.domain.interfaces.outgoing;

import com.api.domain.entities.Client;
import com.api.domain.services.util.Response;

public interface IUserRepository {
    Response<Client> getById(Long id);
}
