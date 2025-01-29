package com.api.app.controller;

import com.api.app.dto.PersonalExpenseDTO;
import com.api.domain.interfaces.incoming.IPersonalExpenserService;
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
    public void createPersonalExpenser(@RequestBody PersonalExpenseDTO dto){
        iPersonalExpenserService.createPersonalExpenses(dto);
    };
}
