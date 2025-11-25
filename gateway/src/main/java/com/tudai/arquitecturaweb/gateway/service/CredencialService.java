    package com.tudai.arquitecturaweb.gateway.service;

    import com.tudai.arquitecturaweb.gateway.dto.CredencialDTO;
    import com.tudai.arquitecturaweb.gateway.entity.Credencial;
    import com.tudai.arquitecturaweb.gateway.repository.AuthorityRepository;
    import com.tudai.arquitecturaweb.gateway.repository.CredencialRepository;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    @Transactional
    public class CredencialService {

        private final CredencialRepository credencialRepository;
        private final PasswordEncoder passwordEncoder;
        private final AuthorityRepository authorityRepository;

        public Integer saveCredencial(CredencialDTO request) {
            final var credencial = new Credencial(request.getDni());
            credencial.setPassword(passwordEncoder.encode(request.getPassword()));
            final var roles = this.authorityRepository.findAllById(request.getAuthorities());
            credencial.setAuthorities( roles );
            final var result = this.credencialRepository.save(credencial);
            return result.getDni();
        }
}
