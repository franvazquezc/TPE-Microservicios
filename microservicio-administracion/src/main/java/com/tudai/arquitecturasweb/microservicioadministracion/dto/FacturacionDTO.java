package com.tudai.arquitecturasweb.microservicioadministracion.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturacionDTO {
    private double totalFacturado;
    private int desdeMes;
    private int hastaMes;
    private int anio;
}
