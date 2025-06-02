package com.api.integration.factory;

import com.api.auth.app.dto.BillDTO;
import com.api.auth.app.dto.LoginDTO;
import com.api.bill.app.dto.PExpenserDTO;
import com.api.bill.app.dto.PersonalExpenseDTO;
import com.api.auth.app.dto.RegisterDTO;
import com.api.auth.domain.entity.Client;
import com.api.auth.domain.incoming.IAuthService;
import com.api.bill.domain.incoming.IBillsService;
import com.api.personalExpenser.domain.incoming.IPersonalExpenserService;
import com.api.util.Response;
import static com.api.unitest.fixture.BillFixture.billDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FactoryData {

    @Autowired
    private IAuthService authService;

    @Autowired
    private IBillsService billsService;

    @Autowired
    private IPersonalExpenserService personalExpenserService;

    public Client createClient() {
        RegisterDTO regiterDTO = new RegisterDTO("Ruperto", "Ramírez", "ruper@gmail.com", "312", "12345");

        Response<Client> client = authService.register(regiterDTO);
        return client.getData();
    }

    public String loginClient(Client client) {
        LoginDTO loginDTO = new LoginDTO("ruper@gmail.com", "12345");
        
        Response<String> token = authService.login(loginDTO);

        return token.getData();
    }

    public void personalExpenser(Client client) {
        List<PersonalExpenseDTO> personalExpenses = new ArrayList<>();
        PersonalExpenseDTO personalExpenseDTO = new PersonalExpenseDTO();
        personalExpenseDTO.setClient(client.getId());
        personalExpenseDTO.setNameProduct("Pan");
        personalExpenseDTO.setPrice(10000D);

        personalExpenses.add(personalExpenseDTO);
        personalExpenserService.createPersonalExpenses(personalExpenseDTO);
    }

    public Long createBill(Client client) {
        
        List<PExpenserDTO> personalExpenses = new ArrayList<>();
        PExpenserDTO personalExpenseDTO = new PExpenserDTO(client.getId(),"Pan",10000D);
        personalExpenses.add(personalExpenseDTO);

        billDTO = BillDTO.builder()
                .event("cumpleaños")
                .mesa("10")
                .personalExpenses(personalExpenses)
                .build();
        Response<Long> resp = billsService.createBill (billDTO);
        return resp.getData();
    }

    
}

