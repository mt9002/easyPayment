package com.api.bill.app.service;

import com.api.bill.presentation.dto.BillDTO;
import com.api.bill.presentation.dto.PExpenserDTO;
import com.api.bill.infra.repository.entityJpa.BillJPA;
import com.api.bill.domain.entity.Bill;
import com.api.bill.domain.entity.PersonalExpenses;
import java.util.HashSet;
import java.util.Set;

public class BillMapper {

    public Bill createBill(BillDTO dto) {
        Bill bill = new Bill(dto.getEvent(), dto.getMesa());
        
        if (dto.getPersonalExpenses() != null) {
            Set<PersonalExpenses> personalExpensesSet = new HashSet<>();

            for (PExpenserDTO pe : dto.getPersonalExpenses()) {

                PersonalExpenses personalExpenses = new PersonalExpenses(
                        pe.getNameProduct(), pe.getPrice(), pe.getClient(), bill);

                personalExpensesSet.add(personalExpenses);
            }
            bill.setPersonalExpenses(personalExpensesSet);
            
        }
        
        return bill;
    }

    public BillJPA updateBill(BillDTO dto) {
        BillJPA bill = new BillJPA(
                dto.getEvent(),
                dto.getMesa()
        );
        bill.setId(dto.getId());
        return bill;
    }
}
