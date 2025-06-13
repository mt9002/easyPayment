package com.api.auth.infra.presistence;

import com.api.auth.infra.presistence.entityJpa.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientORM extends JpaRepository<Client, Long>{
    Optional<Client> findByEmail(String email);
}
