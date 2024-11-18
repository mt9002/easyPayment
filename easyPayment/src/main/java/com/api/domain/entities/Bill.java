package com.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String event;
    private String mesa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PersonalExpenses> personalExpenses;

    public Bill(String event, String mesa) {
        this.event = event;
        this.mesa = mesa;
       
    }
    public Bill(){};
}
