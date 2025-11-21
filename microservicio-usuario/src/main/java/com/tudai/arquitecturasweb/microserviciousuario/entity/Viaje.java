package com.tudai.arquitecturasweb.microserviciousuario.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {
    private Long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<Long> minutosPausas;
    private double kmRecorridos;
    private boolean activo;
    // Relacion con otros microservicios
    private int idUsuario;
    private Long idCuenta;
    private Long idMonopatin;
    private Long idParadaInicial;
    private Long idParadaFinal;
}