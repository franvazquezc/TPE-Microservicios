package com.tudai.arquitecturasweb.microservicioparada.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Parada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private Double latitud;
    @Column(nullable = false)
    private Double longitud;

    public Parada() {}

    public Parada(String direccion, Double latitud, Double longitud) {
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
