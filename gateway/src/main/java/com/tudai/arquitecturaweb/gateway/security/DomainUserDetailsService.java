package com.tudai.arquitecturaweb.gateway.security;

import com.tudai.arquitecturaweb.gateway.entity.Authority;
import com.tudai.arquitecturaweb.gateway.entity.Credencial;
import com.tudai.arquitecturaweb.gateway.repository.CredencialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailService")
public class DomainUserDetailsService  implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final CredencialRepository credencialRepository;

    public DomainUserDetailsService(CredencialRepository credencialRepository) {
        this.credencialRepository = credencialRepository;
    }

    // Spring Security llama este metodo cada vez que necesita autenticar un usuario.
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) {
        log.debug("Authenticating {}", username);
        // Convertir el username (String) del metodo de la interfaz a Integer (dni) del modelo.
        Integer dni;
        try {
            dni = Integer.parseInt(username);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("El usuario " + username + " no es un DNI válido");
        }

        return credencialRepository
                .findOneWithAuthoritiesByDni(dni)
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));
    }

    // Adaptar el modelo de datos al modelo de seguridad de Spring.
    private UserDetails createSpringSecurityUser(Credencial credencial) {
        List<GrantedAuthority> grantedAuthorities = credencial
                .getAuthorities()
                .stream()
                .map(Authority::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(credencial.getDni().toString(), credencial.getPassword(), grantedAuthorities);
    }
}