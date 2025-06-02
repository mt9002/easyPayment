package com.api.bill.app.controller;

import com.api.auth.app.dto.BillDTO;
import com.api.bill.domain.incoming.IBillsService;
import com.api.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bills")
public class BillsController {

    private final IBillsService billsService;

    @Autowired
    public BillsController(IBillsService billsService) {
        this.billsService = billsService;
    }

    @Operation(security = @SecurityRequirement(name = "bearer-key"))
    @PostMapping("/create")
    public ResponseEntity createBill(@RequestBody BillDTO billDTO) {
        
        try {
            Response resp = billsService.createBill(billDTO);

            return ResponseEntity.status(resp.getStatus()).body(resp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response("Error interno: " + e.getMessage(), 400, false, null));
        }  
    }

    @Operation(security = @SecurityRequirement(name = "bearer-key"))
    @GetMapping("/findByIdBill")
    public ResponseEntity findByIdBill(@RequestParam(value = "id") Long id) {
        Response resp = billsService.findById(id);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @Operation(
        summary = "Acceso a un recurso protegido",
        security = @SecurityRequirement(name = "bearer-key")  // Requiere JWT
    )
    
    @DeleteMapping("/delete")
    public ResponseEntity deleteBill(@RequestParam(value = "id") Long id) {
        Response resp = billsService.deleteBill(id);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody BillDTO billDTO) {
        Response resp = billsService.updateBill(billDTO);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

}