package com.api.bill.app.service;

import com.api.bill.presentation.dto.BillDTO;
import com.api.bill.presentation.dto.PExpenserDTO;
import com.api.bill.infra.repository.entityJpa.BillJPA;
import com.api.bill.domain.entity.Bill;
import com.api.bill.domain.entity.PersonalExpenses;
import java.util.HashSet;
import java.util.Set;

public class BillMapper<T> {
    private Bill bill;

    public BillMapper(Bill bill) {
        this.bill = bill;
    }

    public static <T> BillMapper<Bill> toBill(BillDTO dto) {
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

        return new BillMapper<>(bill);
    }

    public static<T> BillMapper<Bill> toBill(BillDTO dto, String update) {
        Bill bill = new Bill(
                dto.getEvent(),
                dto.getMesa());
        bill.setId(dto.getId());
        return new BillMapper<>(bill);
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
