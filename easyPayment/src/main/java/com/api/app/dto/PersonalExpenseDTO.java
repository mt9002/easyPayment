package com.api.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonalExpenseDTO {
    
    private Long idBill;
    private Long client;
    private String nameProduct;
    private Double price;
}
