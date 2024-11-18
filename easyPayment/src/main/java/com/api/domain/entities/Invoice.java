package com.api.domain.entities;

import java.util.List;

public class Invoice {
    
    private String codeInvoce;
    private List<PersonalExpenses> personalExpenses;
    private Long total;

    public List<PersonalExpenses> getPersonalExpenses() {
        return personalExpenses;
    }

    public void setPersonalExpenses(List<PersonalExpenses> personalExpenses) {
        this.personalExpenses = personalExpenses;
    }

    public String getCodeInvoce() {
        return codeInvoce;
    }

    public void setCodeInvoce(String codeInvoce) {
        this.codeInvoce = codeInvoce;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
    
    
}
