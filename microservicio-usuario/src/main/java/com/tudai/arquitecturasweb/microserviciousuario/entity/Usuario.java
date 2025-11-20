package com.tudai.arquitecturasweb.microserviciousuario.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @Column(nullable = false)
    private int dni;
    @Column(nullable = false)
    private String alias;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private int celular;

    // Relacion con otros microservicios.
    @ElementCollection
    @CollectionTable(name = "usuario_cuentas", joinColumns = @JoinColumn(name = "id_usuario"))
    @Column
    private List<Long> idCuentas;
}
