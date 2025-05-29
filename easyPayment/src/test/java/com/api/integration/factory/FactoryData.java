package com.api.integration.factory;

import com.api.app.dto.BillDTO;
import com.api.app.dto.LoginDTO;
import com.api.app.dto.PExpenserDTO;
import com.api.app.dto.PersonalExpenseDTO;
import com.api.app.dto.RegisterDTO;
import com.api.domain.entities.Client;
import com.api.domain.interfaces.incoming.IAuthService;
import com.api.domain.interfaces.incoming.IBillsService;
import com.api.domain.interfaces.incoming.IPersonalExpenserService;
import com.api.domain.services.util.Response;
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
        System.out.println("haciendo login dos");
        LoginDTO loginDTO = new LoginDTO("ruper@gmail.com", "12345");

        Response<String> token = authService.login(loginDTO);

        return token.getData();
    }

    public void personalExpenser(Client client) {
        System.out.println("Creando gasto personal para cliente: " + client.getEmail());
        System.out.println("Creando gasto personal para cliente: " + client.getId());
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

