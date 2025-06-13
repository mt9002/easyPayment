package com.api.bill.infra.repository;

import com.api.bill.infra.repository.entityJpa.PersonalExpensesJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenserORM extends JpaRepository<PersonalExpensesJPA, Long>{
    
}
