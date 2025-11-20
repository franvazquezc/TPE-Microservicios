package com.tudai.arquitecturasweb.microserviciopago.repository;

import com.tudai.arquitecturasweb.microserviciopago.entity.Cuenta;
import com.tudai.arquitecturasweb.microserviciopago.model.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    @Query("SELECT c.idUsuarios FROM Cuenta c WHERE c.tipo = :tipoCuenta")
    List<List<Integer>> getIdUsuariosByTipoCuenta(@Param("tipoCuenta") TipoCuenta tipoCuenta);
}
