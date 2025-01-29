package com.api.app.controller;

import com.api.app.dto.BillDTO;
import com.api.domain.interfaces.incoming.IBillsService;
import com.api.domain.services.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response createBill(@RequestBody BillDTO billDTO) {
        var response = billsService.createBill(billDTO);
        return response;
    }

    @Operation(security = @SecurityRequirement(name = "bearer-key"))
    @GetMapping("/findByIdBill")
    public Response findByIdBill(@RequestParam(value = "id") Long id) {
        var response = billsService.findById(id);
        return response;
    }

    @Operation(
        summary = "Acceso a un recurso protegido",
        security = @SecurityRequirement(name = "bearer-key")  // Requiere JWT
    )
    @DeleteMapping("/delete")
    public void deleteBill(@RequestParam(value = "id") Long id) {

    }

    @PatchMapping("/update")
    public void update(@RequestBody BillDTO billDTO) {
        billsService.updateBill(billDTO);
    }

}
