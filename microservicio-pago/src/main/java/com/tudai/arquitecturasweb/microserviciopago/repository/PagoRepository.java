package com.tudai.arquitecturasweb.microserviciopago.repository;

import com.tudai.arquitecturasweb.microserviciopago.dto.FacturacionDTO;
import com.tudai.arquitecturasweb.microserviciopago.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    @Query("SELECT new com.tudai.arquitecturasweb.microserviciopago.dto.FacturacionDTO(SUM(p.montoFacturado), :anio, :desdeMes, :hastaMes)" +
            "FROM Pago p " +
            "WHERE YEAR(p.fecha) = :anio AND MONTH(p.fecha) >= :desdeMes AND MONTH(p.fecha) <= :hastaMes")
    FacturacionDTO getFacturacionByFecha(@Param("anio") int anio, @Param("desdeMes") int desdeMes, @Param("hastaMes") int hastaMes);
}