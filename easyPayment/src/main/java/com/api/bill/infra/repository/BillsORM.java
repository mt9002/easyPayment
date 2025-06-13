package com.api.bill.infra.repository;

import com.api.bill.infra.repository.entityJpa.BillJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillsORM extends JpaRepository<BillJPA, Long>{
    
}
