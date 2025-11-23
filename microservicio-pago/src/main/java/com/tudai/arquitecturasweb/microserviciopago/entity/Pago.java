package com.tudai.arquitecturasweb.microserviciopago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    @Id
    private Long idViaje;
    @Column
    private Long idCuenta;
    @Column
    private Instant fecha;
    @Column(nullable = false)
    private double montoFacturado;
    @Column
    private Double kmFacturados;
}
