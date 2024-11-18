package com.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PersonalExpenses {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @ManyToOne
    private Client client;

    private String nameProduct;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    @JsonBackReference
    private Bill bill;

    public PersonalExpenses(){};
    
    public PersonalExpenses(Client client, String nameProduct, Double price, Bill bill) {
        this.client = client;
        this.nameProduct = nameProduct;
        this.price = price;
        this.bill = bill;
    }
}
