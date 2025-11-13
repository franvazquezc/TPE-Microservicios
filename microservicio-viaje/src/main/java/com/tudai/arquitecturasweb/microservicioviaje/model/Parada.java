package com.tudai.arquitecturasweb.microservicioviaje.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parada {
    private Long id;
    private String direccion;
    private double latitud;
    private double longitud;
}