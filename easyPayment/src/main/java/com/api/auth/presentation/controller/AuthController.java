package com.api.auth.presentation.controller;

import com.api.auth.domain.ports.in.IAuthService;
import com.api.auth.presentation.dto.LoginDTO;
import com.api.auth.presentation.dto.RegisterDTO;
import com.api.auth.app.service.util.Response;
import com.api.auth.presentation.util.MapperStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity register(@RequestBody RegisterDTO regiterDTO) {
        Response response = authService.register(regiterDTO);   
        return ResponseEntity.status(MapperStatus.map(response.getState())).body(response);
        }
    

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        Response response = authService.login(loginDTO);
        return ResponseEntity.status(MapperStatus.map(response.getState())).body(response);
    }
}
