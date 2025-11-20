package com.tudai.arquitecturasweb.microserviciopago.model;

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

    public Long getMinTotalesDePausa() {
        if (this.minutosPausas == null || minutosPausas.isEmpty()) {
            return 0L;
        }
        long total = 0;
        for (Long pausa : minutosPausas) {
            if (pausa != null) {
                total += pausa;
            }
        }
        return total;
    }
}