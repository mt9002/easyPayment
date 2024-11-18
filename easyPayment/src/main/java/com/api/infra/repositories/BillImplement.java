package com.api.infra.repositories;

import com.api.domain.entities.Bill;
import com.api.domain.interfaces.outgoing.IBillRepository;
import com.api.domain.interfaces.outgoing.jpaORM.BillsORM;
import com.api.domain.services.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
public class BillImplement implements IBillRepository {

    private final BillsORM billsORM;

    @Autowired
    public BillImplement(BillsORM billsORM) {
        this.billsORM = billsORM;
    }

    @Override
    public Response createBill(Bill bill) {
        try {

            Bill b = billsORM.save(bill);

            return new Response(
                    "Bill creado",
                    HttpStatus.OK.value(),
                    true,
                    b.getId());
        } catch (RuntimeException e) {
            return new Response(
                    "Bill no creado " + e.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),
                    false,
                    null);
        } catch (Exception e) {
            return new Response(
                    "Error interno: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    false,
                    null);
        }
    }

    @Override  
    public Response<Bill> findByIdBill(Long id) {
        try {
            Bill bill = billsORM.findById(id)
                    .orElseThrow(() -> new RuntimeException("ID inválido o no encontrado"));
            System.out.println(bill.getEvent());
            return new Response( 
                    "bill encontrado", 
                    HttpStatus.OK.value(),
                    true,
                    bill);
        } catch (RuntimeException e) {
            return new Response(
                    "bill no encontrado, " + e.getMessage(),
                    HttpStatus.NOT_FOUND.value(),
                    false,
                    null);
        }
    }

    @Override
    public Response updateBill(Bill bill) {

        try {
            billsORM.findById(bill.getId())
                    .orElseThrow(() -> new RuntimeException("bill not found"));

            billsORM.save(bill);

            return new Response(
                    "bill actualizada  ",
                    HttpStatus.OK.value(),
                    true,
                    null);
        } catch (RuntimeException e) {
            return new Response(
                    "bill no actualizada, " + e.getMessage(),
                    HttpStatus.NOT_FOUND.value(),
                    false,
                    null);
        }
    }

    @Override
    public Response deleteBill(Long id) {

        try {
            billsORM.deleteById(id);
            return new Response(
                    "bill eliminada",
                    HttpStatus.OK.value(),
                    true,
                    null);
        } catch (RuntimeException e) {
            return new Response(
                    "bill no eliminado, " + e.getMessage(),
                    HttpStatus.NOT_FOUND.value(),
                    false,
                    null);
        }
    }
}
