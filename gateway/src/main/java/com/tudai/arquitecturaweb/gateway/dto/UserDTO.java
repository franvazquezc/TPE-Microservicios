package com.tudai.arquitecturaweb.gateway.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class UserDTO {
    @NotNull(message = "El usuario es un campo requerido.")
    @NotEmpty(message = "El usuario es un campo requerido.")
    private String username;

    @NotNull(message = "La contraseña es un campo requerido.")
    @NotEmpty(message = "La contraseña es un campo requerido.")
    private String password;

    @NotNull(message = "Los roles son un campo requerido.")
    @NotEmpty(message = "Los roles son un campo requerido.")
    private Set<String> authorities;
}
