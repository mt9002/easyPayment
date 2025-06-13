package com.api.auth.infra.presistence.repository;

import com.api.auth.domain.entity.User;
import com.api.auth.domain.ports.out.IUserRepository;
import com.api.auth.infra.presistence.entityJpa.Client;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.api.auth.infra.presistence.ClientORM;

@Repository
public class UserImplement implements IUserRepository {

    private final ClientORM clientORM;

    @Autowired
    public UserImplement(ClientORM clientORM) {
        this.clientORM = clientORM;
    }

    @Override
    public Optional<User> getById(Long id) {
        return clientORM.findById(id).map(this :: clientToUser);
    }

    @Override
    public Optional<User> getByIdEmail(String email) {
        Optional<User> user = clientORM.findByEmail(email).map(this :: clientToUser);
        return user;
    }

    @Override
    public User register(User user) {
        
        Client clientEntiry = userToClient(user);

        Client client = clientORM.save(clientEntiry);
        
        return clientToUser(client);
    }

    private User clientToUser(Client client) {
        User user = new User(
                client.getName(),
                client.getLastName(),
                client.getEmail(),
                null);
        user.setId(client.getId());
        user.setRole(client.getRole());
        return user;
    }

    private Client userToClient(User user) {
        Client clientJpa = new Client(
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword());
        clientJpa.setRole(user.getRole());
        return clientJpa;
    }
}
