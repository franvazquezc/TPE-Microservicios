package com.tudai.arquitecturasweb.microservicioviaje.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViajesMonopatinDTO {
    private Long idMonopatin;
    private Long cantidadDeViajes;
    private int anio;
}