package com.api.domain.interfaces.incoming;

import com.api.app.dto.LoginDTO;
import com.api.app.dto.RegisterDTO;
import com.api.domain.services.util.Response;

public interface IAuthService {

    public Response register(RegisterDTO regiterDTO);

    public Response login(LoginDTO loginDTO);

}
