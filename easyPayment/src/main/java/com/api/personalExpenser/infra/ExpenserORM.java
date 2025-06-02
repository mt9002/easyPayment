package com.api.personalExpenser.infra;

import com.api.personalExpenser.domain.entity.PersonalExpenses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenserORM extends JpaRepository<PersonalExpenses, Long>{
    
}
