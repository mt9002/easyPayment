package com.api.domain.services.mapper;

import com.api.app.dto.BillDTO;
import com.api.app.dto.PExpenserDTO;
import com.api.domain.entities.Bill;
import com.api.domain.entities.Client;
import com.api.domain.entities.PersonalExpenses;
import java.util.HashSet;
import java.util.Set;

public class BillMapper {

    public Bill createBill(BillDTO dto) {
        Bill bill = new Bill();
        
        if (dto.getEvent() != null) {
            bill.setEvent(dto.getEvent());
            bill.setMesa(dto.getMesa());
        }
        
        if (dto.getPersonalExpenses() != null) {
            Set<PersonalExpenses> personalExpensesSet = new HashSet<>();

            System.out.println("llego a mapper");
            System.out.println(dto.getEvent());
            for (PExpenserDTO pe : dto.getPersonalExpenses()) {

                Client client = new Client();
                client.setId(pe.getClient());

                PersonalExpenses personalExpenses = new PersonalExpenses(
                        client,
                        pe.getNameProduct(),
                        pe.getPrice(),
                        bill);

                personalExpensesSet.add(personalExpenses);
            }
            bill.setPersonalExpenses(personalExpensesSet);
            return bill;
        }
        
//        try {
//            bill.getEvent().getClass();
//        } catch (Exception e) {
//            return null;
//        }
        
        return null;
    }

    public Bill updateBill(BillDTO dto) {
        Bill bill = new Bill(
                dto.getEvent(),
                dto.getMesa()
        );
        bill.setId(dto.getId());
        return bill;
    }
}
