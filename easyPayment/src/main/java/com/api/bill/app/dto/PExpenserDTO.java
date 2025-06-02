
package com.api.bill.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PExpenserDTO {
    private Long client;
    private String nameProduct;
    private Double price;
}
