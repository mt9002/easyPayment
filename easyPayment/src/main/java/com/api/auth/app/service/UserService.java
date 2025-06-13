package com.api.auth.app.service;

import com.api.auth.domain.entity.User;
import com.api.auth.domain.ports.in.IUserService;
import com.api.auth.domain.ports.out.IUserRepository;
import com.api.auth.app.service.util.Response;
import com.api.auth.app.service.util.ResultState;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService implements IUserService {

    private final IUserRepository iUserRepository;

    @Autowired
    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @Override
    public Response getById(Long id) {
        try {
            if (id == null) {
                return Response.failure("invalid id", ResultState.VALIDATION_ERROR);
            }
            Optional<User> user = iUserRepository.getById(id);
            if (user.isEmpty()) {
                return Response.failure("User not found, id " + id, ResultState.NOT_FOUND);
            }
            return Response.success("", user.get());
        } catch (Exception e) {
            return Response.failure("Error ", e.getMessage(), ResultState.FAILURE);
        }
    }
}
