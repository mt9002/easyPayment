package com.api.bill.infra.repository;

import com.api.bill.infra.repository.entityJpa.BillJPA;
import com.api.bill.domain.ports.outgoing.IBillRepository;
import com.api.auth.app.service.util.Response;
import com.api.auth.app.service.util.ResultState;
import com.api.bill.domain.entity.Bill;
import com.api.bill.domain.entity.PersonalExpenses;
import com.api.bill.infra.repository.entityJpa.PersonalExpensesJPA;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BillImplement implements IBillRepository {

    private final BillsORM billsORM;

    @Autowired
    public BillImplement(BillsORM billsORM) {
        this.billsORM = billsORM;
    }

    @Override
    public Optional<Bill> createBill(Bill bill) {
        BillJPA billJpas = billsORM.save(billtoJpa(bill));
        return Optional.of(JpatoBill(billJpas));
    }

    @Override
    public Optional<Bill> findByIdBill(Long id) {

        Optional<Bill> bill = billsORM.findById(id).map(this :: JpatoBill);

        Set<String> printedExpenses = new HashSet<>();
        if (bill.isPresent()) {

            for (PersonalExpenses expense : bill.get().getPersonalExpenses()) {
                String expenseKey = expense.getClient_id()+ expense.getNameProduct();
                if (!printedExpenses.contains(expenseKey)) {
                    System.out.println("Gasto: " + expenseKey);
                    printedExpenses.add(expenseKey);
                }
            }
        } else {
            System.out.println("Factura no encontrada con ID: " + id);
        }
        return bill;
    }

    @Override
    public Response updateBill(BillJPA bill) {

        try {
            BillJPA billNew = billsORM.findById(bill.getId())
                    .orElseThrow(() -> new RuntimeException("bill not found"));

            billNew.setEvent(bill.getEvent());
            billNew.setId(bill.getId());
            billNew.setMesa(bill.getMesa());

            billsORM.save(billNew);

            return Response.success("bill actualizada");
        } catch (RuntimeException e) {
            return Response.failure("bill no actualizada, ", e.getMessage(), ResultState.FAILURE); // (HttpStatus.NOT_FOUND.value()

        }
    }

    @Override
    public Response deleteBill(Long id) {

        try {
            billsORM.deleteById(id);
            return Response.success("bill eliminada");
        } catch (RuntimeException e) {
            return Response.failure("bill no eliminado, ", e.getMessage(), ResultState.FAILURE); // (HttpStatus.NOT_FOUND.value()
        }
    }

private Bill JpatoBill(BillJPA billJPA) {
        Bill bill = new Bill(billJPA.getEvent(), billJPA.getMesa());
        bill.setId(billJPA.getId());
        Set<PersonalExpenses> personalExpenses = new HashSet<>();
        for (PersonalExpensesJPA peJPA : billJPA.getPersonalExpenses()) {
            PersonalExpenses pe = JpatoPersonalExpense(peJPA);
            personalExpenses.add(pe);
        }
        bill.setPersonalExpenses(personalExpenses); 
        return bill;
    }
    private PersonalExpenses JpatoPersonalExpense(PersonalExpensesJPA peJPA) {
        PersonalExpenses pe = new PersonalExpenses();
        pe.setNameProduct(peJPA.getNameProduct());
        pe.setPrice(peJPA.getPrice());
        pe.setAmount(peJPA.getAmount());
        pe.setClient_id(peJPA.getClientId());
        return pe;
    }

    private BillJPA billtoJpa(Bill bill) {
        BillJPA billJpa = new BillJPA(bill.getEvent(), bill.getMesa());
        billJpa.setId(bill.getId());
        Set<PersonalExpensesJPA> personalExpenseToJpa = bill.getPersonalExpenses().stream().map(this::PersonalExpenseToJpa)
                .collect(Collectors.toSet());

        billJpa.setPersonalExpenses(personalExpenseToJpa);
        return billJpa;
    }

    private PersonalExpensesJPA PersonalExpenseToJpa(PersonalExpenses pe) {
        PersonalExpensesJPA peJPA = new PersonalExpensesJPA();
        peJPA.setNameProduct(pe.getNameProduct());
        peJPA.setPrice(pe.getPrice());
        peJPA.setAmount(pe.getAmount());
        peJPA.setClientId(pe.getClient_id()); // Asigna el ID del cliente
        return peJPA;
    }
}
