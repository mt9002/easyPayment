package com.api.bill.presentation;

import com.api.bill.presentation.dto.PersonalExpenseDTO;
import com.api.bill.domain.ports.incoming.IPersonalExpenserService;
import com.api.auth.app.service.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenser") 
public class PersonalExpenserController {
    
    private final IPersonalExpenserService iPersonalExpenserService;

    @Autowired
    public PersonalExpenserController(IPersonalExpenserService iPersonalExpenserService) {
        this.iPersonalExpenserService = iPersonalExpenserService;
    } 
    
    @PostMapping("/create")
    public Response createPersonalExpenser(@RequestBody PersonalExpenseDTO dto){
        Response response =iPersonalExpenserService.createPersonalExpenses(dto);
        return response;
    };
}
