package com.api.domain.interfaces.incoming;

import com.api.domain.entities.Client;
import com.api.domain.services.util.Response;

public interface IUserService {
    Response<Client> getById(Long id);
}
