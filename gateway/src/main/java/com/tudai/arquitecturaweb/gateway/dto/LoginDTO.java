package com.tudai.arquitecturaweb.gateway.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
    @NotNull( message = "El dni es un campo requerido." )
    private Integer dni;

    @NotNull( message = "La contraseña es un campo requerido." )
    @NotEmpty( message = "La contraseña es un campo requerido." )
    private String password;

    public String toString(){
        return "Username: " + dni + ", Password: [FORBIDDEN] ";
    }
}
