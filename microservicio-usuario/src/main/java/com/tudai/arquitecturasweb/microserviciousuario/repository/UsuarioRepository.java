package com.tudai.arquitecturasweb.microserviciousuario.repository;

import com.tudai.arquitecturasweb.microserviciousuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
}