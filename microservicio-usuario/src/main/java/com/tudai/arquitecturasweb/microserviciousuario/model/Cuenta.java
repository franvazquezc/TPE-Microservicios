package com.tudai.arquitecturasweb.microserviciousuario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    private Long id;
    private TipoCuenta tipo; // BASICA o PREMIUM
    private LocalDate fechaAlta;
    private double saldo;

    // Atributos específicos de Premium
    private Double kmMensualesConsumidos;
    private Double kmMensualesMaximos = 100.0;

    // Relación con otros microservicios
    private Long usuarioId;
}
