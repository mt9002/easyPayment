package com.api.integration.expenser;

import com.api.bill.app.dto.PersonalExpenseDTO;
import com.api.confg.SilentLogsTest;
import com.api.auth.domain.entity.Client;
import com.api.integration.factory.FactoryData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SilentLogsTest
@SpringBootTest
@AutoConfigureMockMvc
public class TestPersonalExpenserController {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FactoryData factoryData;

    @Test
    @Transactional
    @Rollback
    public void testCreatePersonalExpenser(TestInfo testInfo, TestReporter testReporter) throws Exception {

        Client client = factoryData.createClient();
        Long idBill = factoryData.createBill(client);
        String token = factoryData.loginClient(client);

        PersonalExpenseDTO personalExpenses = new PersonalExpenseDTO(idBill, client.getId(), "Pastel", 2500D);

        String jsonRequest = new ObjectMapper().writeValueAsString(personalExpenses);

        mockMvc.perform(MockMvcRequestBuilders.post("/expenser/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Personal expenses created"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @Transactional
    @Rollback
    public void testCreatePersonalExpenserFailed(TestInfo testInfo, TestReporter testReporter) throws Exception {

        Client client = factoryData.createClient();
        factoryData.createBill(client);
        String token = factoryData.loginClient(client);

        PersonalExpenseDTO personalExpenses = new PersonalExpenseDTO(Long.MAX_VALUE, Long.MAX_VALUE, "Pastel", 2500D);

        String jsonRequest = new ObjectMapper().writeValueAsString(personalExpenses);

        mockMvc.perform(MockMvcRequestBuilders.post("/expenser/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Personal expenses not created"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.success").value(false));
    }
}
