package com.tudai.arquitecturasweb.microservicioviaje.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime fechaInicio;
    @Column
    private LocalDateTime fechaFin;
    @Column(nullable = false)
    private double minutosDePausa;
    @Column(nullable = false)
    private double kmRecorridos;

    // Relacion con otros microservicios
    private Long idUsuario;
    private Long idCuenta;
    private Long idMonopatin;
    private Long idParadaInicial;
    private Long idParadaFinal;

    public Viaje() {
    }

    public Viaje(LocalDateTime fechaInicio, LocalDateTime fechaFin, double minutosDePausa, double kmRecorridos) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.minutosDePausa = minutosDePausa;
        this.kmRecorridos = kmRecorridos;
    }
}