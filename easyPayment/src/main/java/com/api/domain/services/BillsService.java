package com.api.domain.services;

import com.api.app.dto.BillDTO;
import com.api.app.dto.TotalPay;
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
        
        if (response.isSuccess()) {
            Bill data = response.getData();
        Set<PersonalExpenses> personalExpensesSet = data.getPersonalExpenses();

        for (PersonalExpenses object : personalExpensesSet) {
            Client client = object.getClient();
            Double price = object.getPrice();
            totals.put(client, totals.getOrDefault(client, 0.0) + price);
        }

        for (PersonalExpenses ob : personalExpensesSet) {
            System.out.println(ob.getClient() + " " + ob.getNameProduct() + " " + ob.getPrice()
            );
        }

        for (Map.Entry<Client, Double> entry : totals.entrySet()) {
            System.out.println("Name: " + entry.getKey().getName() + ", SubTotal: " + entry.getValue());
            System.out.println(totals.entrySet());
        }
        Map.Entry<Client, Double> entry = totals.entrySet().iterator().next();
        Client client = entry.getKey();
        Double totales = entry.getValue();

        TotalPay totalPay = new TotalPay(client.getId(), client.getName(), totales);
        
        return Response.builder()
                .message(response.getMessage())
                .status(response.getStatus())
                .success(response.isSuccess())
                .data(totalPay)
                .build();
        }
        
        return response;
    }

    @Override
    public Response updateBill(BillDTO billDTO
    ) {
        BillMapper billMapper = new BillMapper();
        Bill bill = billMapper.updateBill(billDTO);
        Response response = billRepository.updateBill(bill);
        return response;
    }

    @Override
    public Response deleteBill(Long id) {
        Response response = billRepository.deleteBill(id);
        return response;
    }
}
