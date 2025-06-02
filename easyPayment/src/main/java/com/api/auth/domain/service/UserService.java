package com.api.auth.domain.service;

import com.api.auth.domain.entity.Client;
import com.api.auth.domain.incoming.IUserService;
import com.api.auth.domain.incoming.IUserService;
import com.api.auth.infra.outgoing.IUserRepository;
import com.api.util.Response;
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

