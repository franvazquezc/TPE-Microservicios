package com.tudai.arquitecturasweb.microservicioparada.repository;

import com.tudai.arquitecturasweb.microservicioparada.entity.Parada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {
}