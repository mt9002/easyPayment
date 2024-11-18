package com.api.domain.interfaces.outgoing;

import com.api.domain.entities.Bill;
import com.api.domain.services.util.Response;

public interface IBillRepository {
    public Response createBill(Bill bill);
    public Response findByIdBill(Long id);
    public Response updateBill(Bill bill);
    public Response deleteBill(Long id);
}
