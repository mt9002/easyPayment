package com.api.integration;

import com.api.app.dto.BillDTO;
import com.api.app.dto.PExpenserDTO;
import com.api.app.dto.TotalPay;
import com.api.domain.entities.Client;
import com.api.integration.factory.FactoryData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestBillControllerIntegration {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FactoryData factoryData;

    static Long idBill;
    static String token;
    static Client client;

    @Test
    @Order(1)
    public void testCreateBillIntegration(TestInfo testInfo, TestReporter testReporter) throws Exception {

        // ARRANGE ---- Preparacion Data
        client = factoryData.createClient();
        token = factoryData.loginClient(client);

        List<PExpenserDTO> personalExpenses = Arrays.asList(new PExpenserDTO(client.getId(), "pizza", 15000D));
        BillDTO billDTO = BillDTO.builder()
                .event("cumpleaños")
                .mesa("10")
                .personalExpenses(personalExpenses)
                .build();

        String jsonRequest = new ObjectMapper().writeValueAsString(billDTO);
           
        MockHttpServletRequestBuilder mockMvcRequest =        // ## simular request
                MockMvcRequestBuilders.post("/bills/create")  // URL
                .contentType(MediaType.APPLICATION_JSON)      // Tipo de contenido
                .header("Authorization", "Bearer " + token)   // Encabezado 
                .content(jsonRequest);                        // Body
        
        // Act (Ejecución) y Assert (Verificación de resultados)
        MvcResult result = mockMvc.perform(mockMvcRequest)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Bill creado"))
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andReturn();
        String response = result.getResponse().getContentAsString();

        idBill = extractJson(response).get("data").asLong();
        // Cleanup (Limpieza, si aplica)  En este test no aplica.
    }

    @Test
    @Order(2)
    public void testfindByIdBillIntegration(TestInfo testInfo, TestReporter testReporter) throws Exception {

        TotalPay data = new TotalPay(client.getId(), client.getName(), 15000);

        mockMvc.perform(MockMvcRequestBuilders.get("/bills/findByIdBill")
                .header("Authorization", "Bearer " + token).param("id", String.valueOf(idBill))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("bill encontrado"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(data));
    }

    @Test
    @Order(3)
    public void testUpdateBillIntegration(TestInfo testInfo, TestReporter testReporter) throws Exception {

        BillDTO billDTO2 = new BillDTO();
        billDTO2.setId(idBill);
        billDTO2.setEvent("cumpleañosJohanna");
        billDTO2.setMesa("12");

        String jsonRequest = new ObjectMapper().writeValueAsString(billDTO2);

        mockMvc.perform(MockMvcRequestBuilders.patch("/bills/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("bill actualizada"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();

    }

    @Test
    @Order(4)
    public void testDeleteIntegration(TestInfo testInfo, TestReporter testReporter) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/bills/delete")
                .header("Authorization", "Bearer " + token).param("id", String.valueOf(idBill))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("bill eliminada"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true));
    }

    // ############################# TEST FAILED ###############################
    @Test
    @Order(5)
    public void testfindByIdBillFailed(TestInfo testInfo, TestReporter testReporter) throws Exception {
        Long idClientFake = 1L;
        Client client3 = factoryData.createClient();
        
        String token2 = factoryData.loginClient(client3);
        mockMvc.perform(MockMvcRequestBuilders.get("/bills/findByIdBill")
                .header("Authorization", "Bearer " + token2).param("id", String.valueOf(idClientFake))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("bill no encontrado, ID inválido o no encontrado"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.success").value(false));
    }

    private JsonNode extractJson(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);

        return jsonNode;
    }
}
