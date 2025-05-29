package com.api.integration;

import com.api.app.dto.BillDTO;
import com.api.domain.entities.Client;
import com.api.domain.interfaces.outgoing.jpaORM.BillsORM;
import com.api.integration.factory.FactoryData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class TestBillFailed {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BillsORM billsORM;

    @Autowired
    FactoryData factoryData;

    @Test
    @Transactional
    @Rollback
    void testCreateBill_InternalServerError(TestInfo testInfo, TestReporter testReporter) throws Exception {
        
        // Arrage
        Client client2 = factoryData.createClient();
        String token2 = factoryData.loginClient(client2);
        BillDTO billDTO = new BillDTO();
        billDTO.setEvent("sdasd");
        billDTO.setMesa("10");
        String jsonRequest = new ObjectMapper().writeValueAsString(billDTO);
        Mockito.when(billsORM.save(Mockito.any())).thenThrow(new RuntimeException("Error simulado"));
        
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/bills/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token2).content(jsonRequest))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.success").value(false));
        
        // CleanUp -- @Transactional and @Rollback
    }

    @Test
    @Transactional
    @Rollback
    public void testfindByIdBillFailed(TestInfo testInfo, TestReporter testReporter) throws Exception {
        Long idClientFake = 9999L;
        Client client3 = factoryData.createClient();
        
        String token2 = factoryData.loginClient(client3);

        Mockito.when(billsORM.findById(Mockito.any())).thenThrow(new RuntimeException("ID inválido o no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/bills/findByIdBill")
                .header("Authorization", "Bearer " + token2).param("id", String.valueOf(idClientFake))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("bill no encontrado, ID inválido o no encontrado"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateBillFailed(TestInfo testInfo, TestReporter testReporter) throws Exception {
        Client client3 = factoryData.createClient();
        
        String token2 = factoryData.loginClient(client3);
        BillDTO billDTO2 = new BillDTO();
        billDTO2.setId(1L);
        billDTO2.setEvent("cumpleañosJohanna");
        billDTO2.setMesa("12");

        String jsonRequest = new ObjectMapper().writeValueAsString(billDTO2);

        mockMvc.perform(MockMvcRequestBuilders.patch("/bills/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token2).content(jsonRequest))
                .andExpect(jsonPath("$.message").value("bill no actualizada, bill not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.success").value(false))
                .andReturn();

    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteFailed(TestInfo testInfo, TestReporter testReporter) throws Exception {
        Client client3 = factoryData.createClient();
        
        String token2 = factoryData.loginClient(client3);

        Mockito.doThrow(new RuntimeException("ID inválido o no encontrado"))
                .when(billsORM).deleteById(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/bills/delete")
                .header("Authorization", "Bearer " + token2).param("id", String.valueOf(client3.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("bill no eliminado, ID inválido o no encontrado"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.success").value(false));
    }
}
