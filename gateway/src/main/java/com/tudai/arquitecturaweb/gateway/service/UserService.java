    package com.tudai.arquitecturaweb.gateway.service;

    import com.tudai.arquitecturaweb.gateway.dto.UserDTO;
    import com.tudai.arquitecturaweb.gateway.entity.User;
    import com.tudai.arquitecturaweb.gateway.repository.AuthorityRepository;
    import com.tudai.arquitecturaweb.gateway.repository.UserRepository;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    @Transactional
    public class UserService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final AuthorityRepository authorityRepository;

        public long saveUser(UserDTO request) {
            final var user = new User(request.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            final var roles = this.authorityRepository.findAllById(request.getAuthorities());
            user.setAuthorities( roles );
            final var result = this.userRepository.save(user);
            return result.getId();
        }


}
