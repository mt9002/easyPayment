package com.api.bill.domain.ports.outgoing;

import com.api.bill.infra.repository.entityJpa.BillJPA;
import com.api.auth.app.service.util.Response;
import com.api.bill.domain.entity.Bill;
import java.util.Optional;

public interface IBillRepository {
    public Optional<Bill> createBill(Bill bill);
    public Optional<Bill> findByIdBill(Long id);
    public Response updateBill(BillJPA bill);
    public Response deleteBill(Long id);
}
