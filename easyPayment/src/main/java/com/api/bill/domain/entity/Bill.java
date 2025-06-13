package com.api.bill.domain.entity;

import java.util.Set;

public class Bill {

    private Long id;
    private String event;
    private String mesa;
    private Set<PersonalExpenses> personalExpenses;

    public Bill(String event, String mesa) {
        this.event = event;
        this.mesa = mesa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public Set<PersonalExpenses> getPersonalExpenses() {
        return personalExpenses;
    }

    public void setPersonalExpenses(Set<PersonalExpenses> personalExpenses) {
        this.personalExpenses = personalExpenses;
    }

}
