package com.api.domain.services;

import com.api.app.dto.PersonalExpenseDTO;
import com.api.domain.entities.Bill;
import com.api.domain.entities.Client;
import com.api.domain.entities.PersonalExpenses;
import com.api.domain.interfaces.incoming.IPersonalExpenserService;
import com.api.domain.interfaces.outgoing.jpaORM.ExpenserORM;
import com.api.domain.services.util.Response;
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
            logger.error("ESTE ES MI ERROR " + e.getMessage());
            return new Response("Personal expenses not created", HttpStatus.BAD_REQUEST.value(), false, null);
        }
    }
}
