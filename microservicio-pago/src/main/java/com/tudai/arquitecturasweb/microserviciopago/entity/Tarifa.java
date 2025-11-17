package com.tudai.arquitecturasweb.microserviciopago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate desde;
    @Column(nullable = false)
    private LocalDate hasta;

    @Column(nullable = false)
    private double tarifaKmPlana;
    @Column(nullable = false)
    private double tarifaKmPremium;
    @Column(nullable = false)
    private double tarifaMensualPremium;
}