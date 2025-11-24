package com.tudai.arquitecturaweb.gateway.repository;

import com.tudai.arquitecturaweb.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

//    @Query("FROM User u JOIN FETCH u.authorities " +
//            "WHERE lower(u.username) = ?1")
//    Optional<User> findOneWithAuthoritiesByUsername(String username);

    @Query("FROM User u JOIN FETCH u.authorities WHERE lower(u.username) = ?1 ")
    Optional<User> findOneWithAuthoritiesByUsernameIgnoreCase(String lowerCase);
}
