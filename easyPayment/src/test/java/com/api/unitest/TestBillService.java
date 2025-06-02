package com.api.unitest;

import com.api.bill.infra.outgoing.IBillRepository;
import com.api.bill.domain.service.BillsService;
import com.api.util.Response;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestBillService {

    @Mock
    private IBillRepository billsRepo;

    @InjectMocks
    private BillsService billsService;

    @Test
    public void testFindByIdBill(TestInfo testInfo, TestReporter testReporter) throws Exception {

        // Arrange
        Long idFake = Long.MAX_VALUE;

        Response respFake = new Response("Bill encontrado firme", 200, true, null);

        Mockito.when(billsRepo.findByIdBill(idFake)).thenReturn(respFake);

        // Act
        Response resp = billsService.findById(idFake);
        
        // Assert
        assertEquals(200, resp.getStatus());
        assertTrue(resp.isSuccess());
        assertEquals("Bill encontrado firme", resp.getMessage()); 

    };
}
