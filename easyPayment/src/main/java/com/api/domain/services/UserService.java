package com.api.domain.services;

import com.api.domain.entities.Client;
import com.api.domain.interfaces.incoming.IUserService;
import com.api.domain.interfaces.outgoing.IUserRepository;
import com.api.domain.services.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    private final IUserRepository iUserRepository;

    @Autowired
    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }
    
    @Override
    public Response<Client> getById(Long id) {
        Response<Client> resnponse  = iUserRepository.getById(id);
        return resnponse;
    }
}

