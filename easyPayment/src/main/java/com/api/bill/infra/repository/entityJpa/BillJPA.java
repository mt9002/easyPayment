package com.api.bill.infra.repository.entityJpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bill")
public class BillJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String event;

    private String mesa;

    @OneToMany(
            //mappedBy = "bill",            // El campo en PersonalExpensesJPA que referencia a BillJPA
            cascade = CascadeType.ALL, // Para que al guardar/eliminar factura se guarden/eliminan los gastos asociados
            orphanRemoval = true, // Para eliminar gastos que se quiten de la colección
            fetch = FetchType.LAZY // Carga perezosa para evitar cargar gastos si no es necesario
    )
    @JoinColumn(name = "bill_id")
    private Set<PersonalExpensesJPA> personalExpenses;

    public BillJPA(String event, String mesa) {
        this.event = event;
        this.mesa = mesa;

    }

    public BillJPA() {
    }
    
}
