package com.api.unitest.fixture;

import com.api.auth.app.dto.BillDTO;
import com.api.auth.app.dto.LoginDTO;
import com.api.bill.app.dto.PExpenserDTO;
import com.api.auth.app.dto.RegisterDTO;
import com.api.auth.domain.entity.Client;
import com.api.auth.domain.entity.Role;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.stereotype.Component;

@Component
public class DTO {

    public static BillDTO billDTO;
    public static LoginDTO loginDTO;
    public static RegisterDTO registerDTO;
    public static Client client;

    @BeforeAll
    public static void beforeAll() {
        List<PExpenserDTO> personalExpenses = Arrays.asList(new PExpenserDTO(1L, "pizza", 15000D));

        billDTO = BillDTO.builder()
                .event("cumpleaños")
                .mesa("10")
                .personalExpenses(personalExpenses)
                .build();

        loginDTO = new LoginDTO(
                "mao@gmail.com",
                "12345678");

        registerDTO = new RegisterDTO(
                "Ruperto",
                "Buen dia",
                "ruper@gmail.com",
                "31255",
                "12345678");

        client = new Client("mao","tovar","mao@gmail.com", "12345");
        client.setName("mao");
        client.setPassword("12345");
        client.setLastName("tovar");
        client.setEmail("mao@gmail.com");
        client.setRole(Role.valueOf("USER"));
    }

}
