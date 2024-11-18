package com.api.app.controller;

import com.api.domain.entities.Client;
import com.api.domain.interfaces.incoming.IUserService;
import com.api.domain.services.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
        private final IUserService iUserService; 

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }
    
    @GetMapping("/findById")
    public Response<Client> getById(@RequestParam(value = "id") Long id){
        var response = iUserService.getById(id);
        return response;
    } 
}
