package com.tudai.arquitecturasweb.microserviciousuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaAPI<T> {
    private boolean ok;
    private String mensaje;
    private T datos;
}
