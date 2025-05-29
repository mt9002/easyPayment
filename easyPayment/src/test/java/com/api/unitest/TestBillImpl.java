package com.api.unitest;

import com.api.app.controller.BillsController;
import com.api.app.dto.BillDTO;
import com.api.confg.NoSecurityConfig;
import com.api.domain.entities.Bill;
import com.api.domain.interfaces.incoming.IBillsService;
import com.api.domain.interfaces.outgoing.IBillRepository;
import com.api.domain.interfaces.outgoing.IJWT;
import com.api.domain.services.util.Response;
import com.api.unitest.fixture.BillFixture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@WebMvcTest(BillsController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(NoSecurityConfig.class)
public class TestBillImpl {

//    @MockBean
//    private IBillRepository billRepository;
//
//    @BeforeAll
//    public static void setup() {
//        BillFixture.beforeAll();
//    }
//
//    @MockBean
//    IJWT jwtService;
//
//    @Test
//    void testCreateBill_InternalServerError() {
//
//        Bill bill = new Bill();
//
//        Response response = new Response("factura no creada", 500, false, null);
//        
//        Mockito.when(billRepository.createBill(bill)).thenReturn(response);
//        
//        Response res = billRepository.createBill(bill);
//        
//        assertEquals(500, res.getStatus());
//        assertFalse(res.isSuccess());
//        assertNull(res.getData());
//        assertTrue(res.getMessage().contains("Error interno"));
//    }

}
