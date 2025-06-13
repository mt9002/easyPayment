package com.api.bill.domain.entity;

public class PersonalExpenses {
    
    private String nameProduct;
    private Double price;
    private Integer amount;
    private Long client_id;
    private Bill bill; 
    
    public PersonalExpenses(){};

    public PersonalExpenses(String nameProduct, Double price, Long client_id, Bill bill) {
        this.nameProduct = nameProduct;
        this.client_id = client_id;
        this.price = price;
        this.bill = bill;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    
    
}
