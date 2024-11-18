package com.api.domain.interfaces.incoming;

import com.api.app.dto.LoginDTO;
import com.api.app.dto.RegiterDTO;
import com.api.domain.services.util.Response;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {

    public Response register(RegiterDTO regiterDTO);

    public Response login(LoginDTO loginDTO);

}
