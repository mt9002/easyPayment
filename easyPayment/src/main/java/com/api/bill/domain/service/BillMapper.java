package com.api.bill.domain.service;

import com.api.auth.app.dto.BillDTO;
import com.api.bill.app.dto.PExpenserDTO;
import com.api.bill.domain.entity.Bill;
import com.api.auth.domain.entity.Client;
import com.api.personalExpenser.domain.entity.PersonalExpenses;
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
