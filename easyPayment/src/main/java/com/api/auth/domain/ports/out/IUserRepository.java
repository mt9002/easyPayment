package com.api.auth.domain.ports.out;

import com.api.auth.domain.entity.User;
import java.util.Optional;

public interface IUserRepository {
    public Optional<User> getById(Long id);
    public Optional<User> getByIdEmail(String email);
    public User register(User user);
}
