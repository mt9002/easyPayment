package com.api.domain.services.mapper;

import com.api.app.dto.BillDTO;
import com.api.app.dto.PExpenserDTO;
import com.api.app.dto.PersonalExpenseDTO;
import com.api.domain.entities.Bill;
import com.api.domain.entities.Client;
import com.api.domain.entities.PersonalExpenses;
import java.util.HashSet;
import java.util.Set;

public class BillMapper {

    public Bill createBill(BillDTO dto) {

        Set<PersonalExpenses> personalExpensesSet = new HashSet<>();
        
        Bill bill = new Bill(
                dto.getEvent(),
                dto.getMesa()
        );
        
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

    public Bill updateBill(BillDTO dto) {
        Bill bill = new Bill();
        return bill;
    }
}
