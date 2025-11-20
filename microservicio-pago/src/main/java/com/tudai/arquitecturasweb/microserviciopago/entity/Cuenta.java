package com.tudai.arquitecturasweb.microserviciopago.entity;

import com.tudai.arquitecturasweb.microserviciopago.model.TipoCuenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCuenta tipo; // BASICA o PREMIUM o ANULADA

    @Column(nullable = false)
    private LocalDate fechaAlta;

    @Column(nullable = false)
    private double saldo;

    // Atributos específicos de Premium
    @Column(nullable = true)
    private Double kmMensualesDisponibles;

    // Relación con otros microservicios
    @ElementCollection
    @CollectionTable(name = "cuenta_usuarios", joinColumns = @JoinColumn(name = "id_cuenta"))
    @Column
    private List<Integer> idUsuarios;

    public Cuenta(TipoCuenta tipo, LocalDate fechaAlta, double saldo, List<Integer> idUsuarios) {
        this.tipo = tipo;
        this.fechaAlta = fechaAlta;
        this.saldo = saldo;
        this.idUsuarios = idUsuarios;
    }

    public Cuenta(TipoCuenta tipo, LocalDate fechaAlta, double saldo, Double kmMensualesDisponibles, List<Integer> idUsuarios) {
        this.tipo = tipo;
        this.fechaAlta = fechaAlta;
        this.saldo = saldo;
        this.kmMensualesDisponibles = kmMensualesDisponibles;
        this.idUsuarios = idUsuarios;
    }
}
