package com.tudai.arquitecturasweb.microserviciomonopatin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {
    private Long id;
    private Instant fechaInicio;
    private Instant fechaFin;
    private List<Long> minutosPausas;
    private double kmRecorridos;
    private boolean activo;
    // Relacion con otros microservicios
    private Integer idUsuario;
    private Long idCuenta;
    private Long idMonopatin;
    private Long idParadaInicial;
    private Long idParadaFinal;
}