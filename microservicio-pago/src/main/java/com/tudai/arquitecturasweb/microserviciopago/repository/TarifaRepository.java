package com.tudai.arquitecturasweb.microserviciopago.repository;

import com.tudai.arquitecturasweb.microserviciopago.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    @Query( "SELECT t FROM Tarifa t WHERE :fecha BETWEEN t.desde AND t.hasta")
    Tarifa getTarifaByFecha(@Param("fecha") LocalDate fecha);
}
