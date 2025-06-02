package com.api.auth.infra.outgoing;

import com.api.auth.domain.entity.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserORM extends JpaRepository<Client, Long>{
    Optional<Client> findByEmail(String email);
}
