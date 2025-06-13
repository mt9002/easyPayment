package com.api.auth.domain.ports.in;

import com.api.auth.app.service.util.Response;

public interface IUserService {
    Response getById(Long id);
}
