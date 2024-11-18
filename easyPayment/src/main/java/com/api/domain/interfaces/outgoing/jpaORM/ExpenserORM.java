package com.api.domain.interfaces.outgoing.jpaORM;

import com.api.domain.entities.PersonalExpenses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenserORM extends JpaRepository<PersonalExpenses, Long>{
    
}
