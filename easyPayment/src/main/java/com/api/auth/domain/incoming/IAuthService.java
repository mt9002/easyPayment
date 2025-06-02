package com.api.auth.domain.incoming;

import com.api.auth.app.dto.LoginDTO;
import com.api.auth.app.dto.RegisterDTO;
import com.api.util.Response;

public interface IAuthService {

    public Response register(RegisterDTO regiterDTO);

    public Response login(LoginDTO loginDTO);

}
