package com.tudai.arquitecturasweb.microserviciomonopatin.repository;

import com.tudai.arquitecturasweb.microserviciomonopatin.dto.KmMonopatinDTO;
import com.tudai.arquitecturasweb.microserviciomonopatin.entity.Monopatin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {

    @Query("SELECT new com.tudai.arquitecturasweb.microserviciomonopatin.dto.KmMonopatinDTO(m.id, m.kmRecorridos) " +
            "FROM Monopatin m")
    List<KmMonopatinDTO> getKmMonopatines();
}