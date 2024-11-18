package com.api.domain.interfaces.outgoing.jpaORM;

import com.api.domain.entities.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserORM extends JpaRepository<Client, Long>{
    Optional<Client> findByEmail(String email);
}
