package com.tudai.arquitecturasweb.microservicioviaje.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViajesUsuarioDTO {
    private Integer idUsuario;
    private Long cantidadViajes;
}
