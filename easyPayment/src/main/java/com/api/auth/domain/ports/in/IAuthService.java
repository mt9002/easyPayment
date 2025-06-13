package com.api.auth.domain.ports.in;

import com.api.auth.presentation.dto.LoginDTO;
import com.api.auth.presentation.dto.RegisterDTO;
import com.api.auth.app.service.util.Response;

public interface IAuthService {

    public Response register(RegisterDTO regiterDTO);

    public Response login(LoginDTO loginDTO);

}
