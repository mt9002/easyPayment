package com.api.unitest.fixture;

import com.api.bill.presentation.dto.BillDTO;
import com.api.auth.presentation.dto.LoginDTO;
import com.api.bill.presentation.dto.PExpenserDTO;
import com.api.auth.presentation.dto.RegisterDTO;
import com.api.auth.infra.presistence.entityJpa.Client;
import com.api.auth.domain.entity.Role;
import com.api.auth.domain.entity.User;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.stereotype.Component;

@Component
public class DTO {

    public static BillDTO billDTO;
    public static LoginDTO loginDTO;
    public static RegisterDTO registerDTO;
    public static User user;
    public static Client userJpa;

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

        user = new User("mao", "tovar", "mao@gmail.com", "12345");
        user.setName("mao");
        user.setPassword("12345");
        user.setLastName("tovar");
        user.setEmail("mao@gmail.com");
        user.setRole(Role.valueOf("USER"));

        userJpa = new Client("mao", "tovar", "mao@gmail.com", "12345");
        userJpa.setRole(Role.valueOf("USER"));
        
    }

}
