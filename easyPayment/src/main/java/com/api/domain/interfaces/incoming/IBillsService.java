package com.api.domain.interfaces.incoming;

import com.api.app.dto.BillDTO;
import com.api.domain.services.util.Response;

public interface IBillsService {
    public Response createBill(BillDTO billDTO);
    public Response findById(Long id); 
    public Response updateBill(BillDTO billDTO);
    public Response deleteBill(Long id);
}
