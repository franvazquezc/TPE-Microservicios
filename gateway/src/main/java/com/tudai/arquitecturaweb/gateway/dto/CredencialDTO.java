package com.tudai.arquitecturaweb.gateway.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class CredencialDTO {
    @NotNull(message = "El dni es un campo requerido.")
    private Integer dni;

    @NotNull(message = "La contraseña es un campo requerido.")
    @NotEmpty(message = "La contraseña es un campo requerido.")
    private String password;

    @NotNull(message = "Los roles son un campo requerido.")
    @NotEmpty(message = "Los roles son un campo requerido.")
    private Set<String> authorities;
}
