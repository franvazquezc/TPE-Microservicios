package com.tudai.arquitecturasweb.microserviciopago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate desde;
    @Column(nullable = false)
    private LocalDate hasta;

    @Column(nullable = false)
    private double tarifaXMinPlana;
    @Column(nullable = false)
    private double tarifaXMinExtra;
    @Column(nullable = false)
    private double tarifaXMinPremium;
    @Column(nullable = false)
    private double tarifaXMesPremium;

    public Tarifa(LocalDate desde,
                  LocalDate hasta,
                  double tarifaXMinPlana,
                  double tarifaXMinExtra,
                  double tarifaXMinPremium,
                  double tarifaXMesPremium) {
        this.desde = desde;
        this.hasta = hasta;
        this.tarifaXMinPlana = tarifaXMinPlana;
        this.tarifaXMinExtra = tarifaXMinExtra;
        this.tarifaXMinPremium = tarifaXMinPremium;
        this.tarifaXMesPremium = tarifaXMesPremium;
    }
}