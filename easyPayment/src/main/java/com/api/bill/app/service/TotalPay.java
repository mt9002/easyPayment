package com.api.bill.app.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalPay {
    Long idClient;
    String name;
    double total;
}
