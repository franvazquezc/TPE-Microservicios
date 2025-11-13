package com.tudai.arquitecturasweb.microservicioviaje.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private int dni;
    private String alias;
    private String nombre;
    private String apellido;
    private String email;
    private int celular;
    private List<Long> idCuentas;
}