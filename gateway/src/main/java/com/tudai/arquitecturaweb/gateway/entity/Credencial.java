package com.tudai.arquitecturaweb.gateway.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Credencial {

    @Id
    private Integer dni;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "dni") },
            inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name")}
    )
    private Set<Authority> authorities = new HashSet<>();

    public Credencial(Integer dni) {
        this.dni = dni;
    }

    public void setAuthorities(final Collection<Authority> authorities) {
        this.authorities = new HashSet<>(authorities);
    }
}
