package com.tudai.arquitecturasweb.microserviciomonopatin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parada {
    private Long id;
    private String direccion;
    private Double latitud;
    private Double longitud;
}
