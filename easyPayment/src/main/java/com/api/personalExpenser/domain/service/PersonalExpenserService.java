package com.api.personalExpenser.domain.service;

import com.api.bill.app.dto.PersonalExpenseDTO;
import com.api.bill.domain.entity.Bill;
import com.api.auth.domain.entity.Client;
import com.api.personalExpenser.domain.entity.PersonalExpenses;
import com.api.personalExpenser.domain.incoming.IPersonalExpenserService;
import com.api.personalExpenser.infra.ExpenserORM;
import com.api.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

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
        PersonalExpenses personalExpense = new PersonalExpenses();

        Client client = new Client();
        client.setId(dto.getClient());
        
        Bill bill = new Bill();
        bill.setId(dto.getIdBill());
        
        personalExpense.setNameProduct(dto.getNameProduct());
        personalExpense.setPrice(dto.getPrice());
        personalExpense.setBill(bill);
        personalExpense.setClient(client);
        
        try {
            logger.info("Creando personal expenser");
            PersonalExpenses personalExpenses =expenserORM.save(personalExpense);
            return new Response("Personal expenses created", HttpStatus.OK.value(), true, personalExpenses.getId());
        } catch (Exception e) {
            logger.error("Error al crear gasto personal: {}", e.getMessage());
            return new Response("Personal expenses not created", HttpStatus.BAD_REQUEST.value(), false, null);
        }
    }
}
