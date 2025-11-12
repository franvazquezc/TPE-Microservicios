package com.tudai.arquitecturasweb.microserviciomonopatin.repository;

import com.tudai.arquitecturasweb.microserviciomonopatin.entity.Monopatin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {
}