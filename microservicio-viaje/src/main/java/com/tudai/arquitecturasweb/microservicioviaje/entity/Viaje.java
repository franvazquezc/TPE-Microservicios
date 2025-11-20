package com.tudai.arquitecturasweb.microservicioviaje.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime fechaInicio;
    @Column
    private LocalDateTime fechaFin;
    @ElementCollection
    @CollectionTable(name = "viaje_pausas", joinColumns = @JoinColumn(name = "id_viaje"))
    @Column
    private List<Long> minutosPausas;
    @Column(nullable = false)
    private double kmRecorridos;
    @Column(nullable = false)
    private boolean activo;
    // Relacion con otros microservicios
    @Column(nullable = false)
    private int idUsuario;
    @Column(nullable = false)
    private Long idCuenta;
    @Column(nullable = false)
    private Long idMonopatin;
    @Column(nullable = false)
    private Long idParadaInicial;
    @Column
    private Long idParadaFinal;

    public Viaje(LocalDateTime fechaInicio,
                 List<Long> minutosPausas,
                 double kmRecorridos,
                 int idUsuario,
                 Long idCuenta,
                 Long idMonopatin,
                 Long idParadaInicial) {
        this.fechaInicio = fechaInicio;
        this.minutosPausas = minutosPausas;
        this.kmRecorridos = kmRecorridos;
        this.activo = true;
        this.idUsuario = idUsuario;
        this.idCuenta = idCuenta;
        this.idMonopatin = idMonopatin;
        this.idParadaInicial = idParadaInicial;
    }

    public Viaje(LocalDateTime fechaInicio,
                 LocalDateTime fechaFin,
                 List<Long> minutosPausas,
                 double kmRecorridos,
                 int idUsuario,
                 Long idCuenta,
                 Long idMonopatin,
                 Long idParadaInicial,
                 Long idParadaFinal) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.minutosPausas = minutosPausas;
        this.kmRecorridos = kmRecorridos;
        this.activo = false;
        this.idUsuario = idUsuario;
        this.idCuenta = idCuenta;
        this.idMonopatin = idMonopatin;
        this.idParadaInicial = idParadaInicial;
        this.idParadaFinal = idParadaFinal;
    }
}