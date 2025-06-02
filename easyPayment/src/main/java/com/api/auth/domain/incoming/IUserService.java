package com.api.auth.domain.incoming;

import com.api.auth.domain.entity.Client;
import com.api.util.Response;

public interface IUserService {
    Response<Client> getById(Long id);
}
