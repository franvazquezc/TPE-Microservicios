package com.tudai.arquitecturasweb.microservicioviaje.repository;


import com.tudai.arquitecturasweb.microservicioviaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
}