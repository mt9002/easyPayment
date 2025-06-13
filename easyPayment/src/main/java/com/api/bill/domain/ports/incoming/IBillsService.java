package com.api.bill.domain.ports.incoming;

import com.api.bill.presentation.dto.BillDTO;
import com.api.auth.app.service.util.Response;

public interface IBillsService {
    public Response createBill(BillDTO billDTO);
    public Response findById(Long id); 
    public Response updateBill(BillDTO billDTO);
    public Response deleteBill(Long id);
}
