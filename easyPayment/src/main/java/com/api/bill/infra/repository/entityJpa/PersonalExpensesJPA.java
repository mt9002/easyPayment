package com.api.bill.infra.repository.entityJpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "personal_expenses")
public class PersonalExpensesJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private Long clientId;

    private String nameProduct;
    private Double price;
    private Integer amount;
    
    @Column(name = "bill_id")
    private Long billId;

    public PersonalExpensesJPA(){};
    
    public PersonalExpensesJPA(Long clientId, String nameProduct, Double price) {
        this.clientId = clientId;
        this.nameProduct = nameProduct;
        this.price = price;
        
    }
}
