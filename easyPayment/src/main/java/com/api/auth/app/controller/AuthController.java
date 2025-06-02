package com.api.auth.app.controller;

import com.api.auth.domain.incoming.IAuthService;
import com.api.auth.app.dto.LoginDTO;
import com.api.auth.app.dto.RegisterDTO;
import com.api.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Response register(@RequestBody RegisterDTO regiterDTO) {
        return authService.register(regiterDTO);
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}
