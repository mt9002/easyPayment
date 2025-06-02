package com.api.bill.domain.incoming;

import com.api.auth.app.dto.BillDTO;
import com.api.util.Response;

public interface IBillsService {
    public Response createBill(BillDTO billDTO);
    public Response findById(Long id); 
    public Response updateBill(BillDTO billDTO);
    public Response deleteBill(Long id);
}
