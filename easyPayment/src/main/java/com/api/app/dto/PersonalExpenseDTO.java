package com.api.app.dto;

import lombok.Data;

@Data
public class PersonalExpenseDTO {
    
    private Long idBill;
    private Long client;
    private String nameProduct;
    private Double price;
}
