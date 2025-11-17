package com.tudai.arquitecturasweb.microserviciopago.repository;

import com.tudai.arquitecturasweb.microserviciopago.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
}
