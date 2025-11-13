package com.tudai.arquitecturasweb.microservicioviaje.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    private Long id;
    // Estado
    private boolean activo;
    private EstadoMonopatin estado; // ESTACIONADO, EN_USO, PAUSA, MANTENIMIENTO

    // Estadisticas de uso
    private long minutosDeUso;
    private double kmRecorridos;

    // Ubicacion
    private Double latitud;
    private Double longitud;

    // Relacion con otros microservicios
    private Long idViajeActual;
    private Long idParadaActual;
}
