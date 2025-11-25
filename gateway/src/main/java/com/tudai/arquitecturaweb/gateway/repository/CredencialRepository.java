package com.tudai.arquitecturaweb.gateway.repository;

import com.tudai.arquitecturaweb.gateway.entity.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredencialRepository extends JpaRepository<Credencial,Integer> {

    @Query("FROM Credencial c JOIN FETCH c.authorities WHERE c.dni = ?1 ")
    Optional<Credencial> findOneWithAuthoritiesByDni(Integer dni);
}
