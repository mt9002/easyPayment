package com.api.bill.infra.outgoing;

import com.api.bill.domain.entity.Bill;
import com.api.util.Response;

public interface IBillRepository {
    public Response createBill(Bill bill);
    public Response findByIdBill(Long id);
    public Response updateBill(Bill bill);
    public Response deleteBill(Long id);
}
