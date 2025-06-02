package com.api.bill.infra.outgoing;

import com.api.bill.domain.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillsORM extends JpaRepository<Bill, Long>{
    
}
