package com.tudai.arquitecturasweb.microserviciopago.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturacionDTO {
    private double monto;
    private int anio;
    private int desdeMes;
    private int hastaMes;
}
