package com.api.bill.app.service;

import com.api.bill.presentation.dto.BillDTO;
import com.api.bill.infra.repository.entityJpa.BillJPA;
import com.api.bill.domain.ports.outgoing.IBillRepository;
import com.api.bill.domain.ports.incoming.IBillsService;
import com.api.auth.app.service.util.Response;
import com.api.auth.app.service.util.ResultState;
import com.api.bill.domain.entity.Bill;
import com.api.bill.domain.entity.PersonalExpenses;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
        if (billRepository.createBill(bill).isEmpty()) {
            Response.failure("Bill no creado", ResultState.NOT_FOUND);
        }
        return Response.success("Bill creado");
    }

    @Override
    public Response findById(Long id) {
        Map<Long, Double> totals = new HashMap<>();

        Optional<Bill> optionalBill = billRepository.findByIdBill(id);
        if (optionalBill.isPresent()) {

            optionalBill.get().getPersonalExpenses()
                    .forEach(bill -> {

                        totals.put(
                                bill.getClient_id(),
                                totals.getOrDefault(bill.getClient_id(), 0.0) + bill.getPrice());
                    });

            List<TotalPay> totalPays = totals.entrySet().stream()
                    .map(entry -> new TotalPay(entry.getKey(), "", entry.getValue()))
                    .collect(Collectors.toList());
            return Response.success("Bill encontrada", totalPays);
        }
        return Response.failure("bill no encontrado", ResultState.NOT_FOUND);
    }

    @Override
    public Response updateBill(BillDTO billDTO
    ) {
        BillMapper billMapper = new BillMapper();
        BillJPA bill = billMapper.updateBill(billDTO);
        Response response = billRepository.updateBill(bill);
        return response;
    }

    @Override
    public Response deleteBill(Long id) {
        Response response = billRepository.deleteBill(id);
        return response;
    }
}
