
package com.tudai.arquitecturasweb.microserviciomonopatin.entity;


import com.tudai.arquitecturasweb.microserviciomonopatin.model.EstadoMonopatin;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Estado
    @Column(nullable = false)
    private boolean activo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMonopatin estado; // ESTACIONADO, EN_USO, PAUSA, MANTENIMIENTO

    // Estadisticas de uso
    @Column(nullable = false)
    private long minutosDeUso;
    @Column(nullable = false)
    private double kmRecorridos;

    // Ubicacion
    @Column(nullable = false)
    private Double latitud;
    @Column(nullable = false)
    private Double longitud;

    // Relacion con otros microservicios
    @Column
    private Long idViajeActual;
    @Column
    private Long idParadaActual;

    public Monopatin(boolean activo, EstadoMonopatin estado, long minutosDeUso, double kmRecorridos, Double latitud, Double longitud) {
        this.activo = activo;
        this.estado = estado;
        this.minutosDeUso = minutosDeUso;
        this.kmRecorridos = kmRecorridos;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Monopatin(boolean activo, EstadoMonopatin estado, long minutosDeUso, double kmRecorridos, Double latitud, Double longitud, Long idViajeActual) {
        this.activo = activo;
        this.estado = estado;
        this.minutosDeUso = minutosDeUso;
        this.kmRecorridos = kmRecorridos;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idViajeActual = idViajeActual;
    }

    public Monopatin(boolean activo, EstadoMonopatin estado, long minutosDeUso, double kmRecorridos, Double latitud, Long idParadaActual, Double longitud) {
        this.activo = activo;
        this.estado = estado;
        this.minutosDeUso = minutosDeUso;
        this.kmRecorridos = kmRecorridos;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idParadaActual = idParadaActual;
    }
}
