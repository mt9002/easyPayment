package com.api.unitest;

import com.api.auth.infra.security.jwt.JWT;
import com.api.bill.presentation.BillsController;
import com.api.bill.presentation.dto.BillDTO;
import com.api.confg.NoSecurityConfig;
import com.api.bill.domain.ports.incoming.IBillsService;
import com.api.auth.app.service.util.Response;
import com.api.auth.app.service.util.ResultState;
import com.api.unitest.fixture.BillFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BillsController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(NoSecurityConfig.class)
public class TestBillsController {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IBillsService billService;

    @BeforeAll
    public static void setup() {
        BillFixture.beforeAll();
    }

    @MockBean
    JWT jwtService;

    @Test
    public void testFindByIdBill(TestInfo testInfo, TestReporter testReporter) throws Exception {

        // Arrange ############################################################################
        Long id_bill = 1L;
        BillDTO data = BillFixture.billDTO;

        Response responseFake = new Response("factura encontrada",  ResultState.SUCCESS, data); // Response quemado fake

        Mockito.when(billService.findById(id_bill)).thenReturn(responseFake);

        MockHttpServletRequestBuilder requestMock = MockMvcRequestBuilders
                .get("/bills/findByIdBill")
                .param("id", String.valueOf(id_bill))
                .contentType(MediaType.APPLICATION_JSON);

        // Act and Assert #####################################################################
        mockMvc.perform(requestMock)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("factura encontrada"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.event").value("cumpleaños"));

    }

    @Test
    public void testCreateBill(TestInfo testInfo, TestReporter testReporter) throws Exception {

        BillDTO billDto = BillFixture.billDTO;

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(billDto);

        Response response = new Response("factura creada",  ResultState.SUCCESS, billDto);

        Mockito.when(billService.createBill(billDto)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/bills/create")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("factura creada"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.event").value("cumpleaños"));

    }

    @Test
    public void testCreateBillException(TestInfo testInfo, TestReporter testReporter) throws Exception {

        BillDTO billDto = BillFixture.billDTO;

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(billDto);

        Mockito.when(billService.createBill(Mockito.any())).thenThrow(new RuntimeException("Error simulado"));

        mockMvc.perform(MockMvcRequestBuilders.post("/bills/create")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("Error interno: Error simulado"))
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    public void testDeleteBill(TestInfo testInfo, TestReporter testReporter) throws Exception {

        Long id_bill = 1L;
        BillDTO data = BillFixture.billDTO;

        Response response = new Response("factura eliminada",  ResultState.SUCCESS, data);

        Mockito.when(billService.deleteBill(id_bill)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.delete("/bills/delete")
                .param("id", String.valueOf(id_bill))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("factura eliminada"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.event").value("cumpleaños"));
    }

    @Test
    public void testUpdateBill(TestInfo testInfo, TestReporter testReporter) throws Exception {

        BillDTO billDto = BillFixture.billDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(billDto);

        Response response = new Response("factura creada",  ResultState.SUCCESS, billDto);

        Mockito.when(billService.updateBill(billDto)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.patch("/bills/update")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("factura creada"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.event").value("cumpleaños"));

    }
}
