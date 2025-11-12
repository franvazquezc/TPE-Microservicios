package com.tudai.arquitecturasweb.microserviciopago.repository;

import com.tudai.arquitecturasweb.microserviciopago.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}