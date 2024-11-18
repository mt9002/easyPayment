package com.api.domain.services;

import com.api.app.dto.BillDTO;
import com.api.domain.entities.Bill;
import com.api.domain.entities.Client;
import com.api.domain.entities.PersonalExpenses;
import com.api.domain.interfaces.incoming.IBillsService;
import com.api.domain.interfaces.outgoing.IBillRepository;
import com.api.domain.services.mapper.BillMapper;
import com.api.domain.services.util.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class BillsService implements IBillsService {

    private final IBillRepository billRepository;

    public BillsService(IBillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Response createBill(BillDTO billDTO) {
        BillMapper billMapper = new BillMapper();
        Bill bill = billMapper.createBill(billDTO);
        Response response = billRepository.createBill(bill);
        return response;
    }

    @Override
    public Response findById(Long id) {
        Response<Bill> response = billRepository.findByIdBill(id);
        Map<Client, Double> totals = new HashMap<>();
        try {
            Bill data = response.getData();
            Set<PersonalExpenses> personalExpensesSet = data.getPersonalExpenses();
            

            for (PersonalExpenses object : personalExpensesSet) {
                Client client = object.getClient();
                Double price = object.getPrice();
                totals.put(client, totals.getOrDefault(client, 0.0) + price);
            }
            for (Map.Entry<Client, Double> entry : totals.entrySet()) {
                System.out.println("Name: " + entry.getKey().getName()+ ", SubTotal: " + entry.getValue());
            }
        } catch (Exception e) { 
            System.out.println(e.getMessage());
        }

        return Response.builder()
                .message("Factura ##")
                .data(totals.entrySet())
                .build();
    }

    @Override
    public Response updateBill(BillDTO billDTO
    ) {
        BillMapper billMapper = new BillMapper();
        Bill bill = billMapper.updateBill(billDTO);
        Response response = billRepository.createBill(bill);
        return response;
    }
}
