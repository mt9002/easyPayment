package com.api.app.dto;

import java.util.List;
import lombok.Data;

@Data
public class BillDTO {
    private String event;
    private String mesa;
    private List<PExpenserDTO> personalExpenses;
}
