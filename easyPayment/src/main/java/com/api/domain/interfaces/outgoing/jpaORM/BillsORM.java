package com.api.domain.interfaces.outgoing.jpaORM;

import com.api.domain.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillsORM extends JpaRepository<Bill, Long>{
    
}
