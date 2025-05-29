package com.api.integration;

import com.api.domain.entities.Client;
import com.api.integration.factory.FactoryData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class TestWebFlexusController {

    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    FactoryData factoryData;

    @Test
    public void calculator() throws Exception {

        // Arrange
        String num1 = "20";
        String num2 = "10";
        Client client = factoryData.createClient();
        String token =factoryData.loginClient(client);

        MockHttpServletRequestBuilder mockRequest
                = MockMvcRequestBuilders
                        .get("/calculadora/operaciones")
                        .contentType(MediaType.APPLICATION_JSON).header("Aut")
                        .param("a", num1)
                        .param("b", num2);
        
        // Act and Assert
        mockMvc.perform(mockRequest);
    }
}
