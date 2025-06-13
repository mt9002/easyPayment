package com.api.bill.app.service;

import com.api.bill.presentation.dto.PersonalExpenseDTO;
import com.api.bill.infra.repository.entityJpa.BillJPA;
import com.api.bill.infra.repository.entityJpa.PersonalExpensesJPA;
import com.api.bill.domain.ports.incoming.IPersonalExpenserService;
import com.api.bill.infra.repository.ExpenserORM;
import com.api.auth.app.service.util.Response;
import com.api.auth.app.service.util.ResultState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PersonalExpenserService implements IPersonalExpenserService {

    private static final Logger logger = LoggerFactory.getLogger(PersonalExpenserService.class);
    private final ExpenserORM expenserORM;
  

    @Autowired
    public PersonalExpenserService(ExpenserORM expenserORM) {
        this.expenserORM = expenserORM;
    }

    @Override
    public Response createPersonalExpenses(PersonalExpenseDTO dto) {
        PersonalExpensesJPA personalExpense = new PersonalExpensesJPA();
        
        personalExpense.setBillId(dto.getIdBill());
        personalExpense.setNameProduct(dto.getNameProduct());
        personalExpense.setPrice(dto.getPrice());
        personalExpense.setClientId(dto.getClient());
        
        try {
            logger.info("Creando personal expenser");
            PersonalExpensesJPA personalExpenses =expenserORM.save(personalExpense);
            return Response.success("Personal expenses created", personalExpenses.getId());
        } catch (Exception e) {
            logger.error("Error al crear gasto personal: {}", e.getMessage());
            return Response.failure("Personal expenses not created",  ResultState.NOT_FOUND);
        }
    }
}
