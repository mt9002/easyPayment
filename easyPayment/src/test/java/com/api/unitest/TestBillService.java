package com.api.unitest;

import com.api.bill.domain.ports.outgoing.IBillRepository;
import com.api.bill.app.service.BillsService;
import com.api.auth.app.service.util.Response;
import com.api.bill.domain.entity.Bill;
import java.util.Optional;
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

        Bill bill = new Bill("party", "12");

        Mockito.when(billsRepo.findByIdBill(idFake)).thenReturn(Optional.of(bill));

        // Act
        Response resp = billsService.findById(idFake);
        
        // Assert
        assertEquals("Bill encontrado firme", resp.getMessage()); 

    };
}
