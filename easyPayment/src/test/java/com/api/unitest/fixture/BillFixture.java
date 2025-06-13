package com.api.unitest.fixture;

import com.api.bill.presentation.dto.BillDTO;
import com.api.bill.presentation.dto.PExpenserDTO;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.stereotype.Component;

@Component
public class BillFixture {

    public static BillDTO billDTO;

    @BeforeAll
    public static void beforeAll() {
        List<PExpenserDTO> personalExpenses = Arrays.asList(new PExpenserDTO(1L, "pizza", 15000D));

        billDTO = BillDTO.builder()
                .event("cumpleaños")
                .mesa("10")
                .personalExpenses(personalExpenses)
                .build();
    }
}
