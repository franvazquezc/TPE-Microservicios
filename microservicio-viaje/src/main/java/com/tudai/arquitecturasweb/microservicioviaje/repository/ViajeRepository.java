package com.tudai.arquitecturasweb.microservicioviaje.repository;


import com.tudai.arquitecturasweb.microservicioviaje.dto.ViajesMonopatinDTO;
import com.tudai.arquitecturasweb.microservicioviaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    @Query("SELECT new com.tudai.arquitecturasweb.microservicioviaje.dto.ViajesMonopatinDTO(v.idMonopatin, COUNT(v), :anio) " +
            "FROM Viaje v " +
            "WHERE YEAR(v.fechaInicio) = :anio " +
            "GROUP BY v.idMonopatin " +
            "HAVING COUNT(v) > :cantidadViajes")
    List<ViajesMonopatinDTO> getMonopatinesConMasDeXViajesEnAnio(@Param("cantidadViajes") int cantidadViajes,
                                                                 @Param("anio") int anio);
}