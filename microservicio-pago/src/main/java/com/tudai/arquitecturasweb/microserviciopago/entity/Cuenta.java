package com.tudai.arquitecturasweb.microserviciopago.entity;

import com.tudai.arquitecturasweb.microserviciopago.model.TipoCuenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Double kmMensualesConsumidos;
    private Double kmMensualesMaximos = 100.0;

    // Relación con otros microservicios
    private Long usuarioId;
}
