package com.api.app.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillDTO {
    private String event;
    private String mesa;
    private List<PExpenserDTO> personalExpenses;
}
