package com.api.auth.app.dto;

import com.api.bill.app.dto.PExpenserDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    private Long id;
    private String event;
    private String mesa;
    private List<PExpenserDTO> personalExpenses;
}
