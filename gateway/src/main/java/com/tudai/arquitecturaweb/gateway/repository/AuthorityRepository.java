package com.tudai.arquitecturaweb.gateway.repository;

import com.tudai.arquitecturaweb.gateway.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
