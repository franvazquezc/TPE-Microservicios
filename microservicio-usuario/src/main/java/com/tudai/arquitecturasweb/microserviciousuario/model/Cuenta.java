package com.tudai.arquitecturasweb.microserviciousuario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    private Long id;
    private TipoCuenta tipo; // BASICA o PREMIUM o ANULADA
    private LocalDate fechaAlta;
    private double saldo;

    // Atributos específicos de Premium
    private Double kmMensualesDisponibles;

    // Relación con otros microservicios
    private List<Integer> idUsuarios;
}
