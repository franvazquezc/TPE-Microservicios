package com.tudai.arquitecturasweb.microserviciopago.repository;

import com.tudai.arquitecturasweb.microserviciopago.entity.Cuenta;
import com.tudai.arquitecturasweb.microserviciopago.model.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    @Query("SELECT c.idUsuarios FROM Cuenta c WHERE c.tipo = :tipoCuenta")
    List<List<Long>> getIdUsuariosByTipoCuenta(@Param("tipoCuenta") TipoCuenta tipoCuenta);
}
