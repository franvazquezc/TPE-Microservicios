package com.tudai.arquitecturasweb.microserviciomonopatin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private double minutosDePausa;
    private double kmRecorridos;

    // Relacion con otros microservicios
    private Long idUsuario;
    private Long idCuenta;
    private Long idMonopatin;
    private Long idParadaInicial;
    private Long idParadaFinal;
}