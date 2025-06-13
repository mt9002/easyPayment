package com.api.auth.presentation.controller;

import com.api.auth.domain.ports.in.IUserService;
import com.api.auth.app.service.util.Response;
import com.api.auth.presentation.util.MapperStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private final IUserService iUserService; 

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }
    
    @GetMapping("/findById")
    public ResponseEntity getById(@RequestParam(value = "id") Long id){
        Response response = iUserService.getById(id);
        return ResponseEntity.status(MapperStatus.map(response.getState())).body(response);
    } 
}