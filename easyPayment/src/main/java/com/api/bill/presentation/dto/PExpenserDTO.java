
package com.api.bill.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PExpenserDTO {
    private Long client;
    private String nameProduct;
    private Double price;
}
