package com.api.auth.app.controller;

import com.api.auth.domain.entity.Client;
import com.api.auth.domain.incoming.IUserService;
import com.api.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response getById(@RequestParam(value = "id") Long id){
        Response<Client> response = iUserService.getById(id);
        return response;
    } 
}
