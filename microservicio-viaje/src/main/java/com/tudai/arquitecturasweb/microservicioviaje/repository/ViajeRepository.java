package com.tudai.arquitecturasweb.microservicioviaje.repository;


import com.tudai.arquitecturasweb.microservicioviaje.dto.ViajesMonopatinDTO;
import com.tudai.arquitecturasweb.microservicioviaje.dto.ViajesUsuarioDTO;
import com.tudai.arquitecturasweb.microservicioviaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
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

    @Query("SELECT new com.tudai.arquitecturasweb.microservicioviaje.dto.ViajesUsuarioDTO(" +
            "v.idUsuario, COUNT(v)) " +
            "FROM Viaje v " +
            "WHERE v.idUsuario IN :idUsuarios " +
            "AND v.fechaInicio BETWEEN :desde AND :hasta " +
            "GROUP BY v.idUsuario " +
            "ORDER BY COUNT(v) DESC")
    List<ViajesUsuarioDTO> getUsuariosMasActivos(
            @Param("idUsuarios") List<Integer> idUsuarios,
            @Param("desde") Instant desde,
            @Param("hasta") Instant hasta);

    @Query("SELECT v.id FROM Viaje v WHERE v.activo = true")
    List<Long> getIdViajesActivos();

    @Query("SELECT COUNT(v) FROM Viaje v " +
            "WHERE v.idUsuario = :idUsuario " +
            "AND v.fechaInicio BETWEEN :desde AND :hasta")
    int getCantidadViajesUsuario(@Param("idUsuario") int idUsuario,
                                 @Param("desde") Instant desde,
                                 @Param("hasta") Instant hasta);
}