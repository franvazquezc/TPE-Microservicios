package com.tudai.arquitecturaweb.gateway.config;

import com.tudai.arquitecturaweb.gateway.security.AuthotityConstant;
import com.tudai.arquitecturaweb.gateway.security.jwt.JwtFilter;
import com.tudai.arquitecturaweb.gateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable);
        http
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        .requestMatchers("/api/monopatines/reporte-km/**").hasAuthority(AuthotityConstant._ADMIN)
                        .requestMatchers("/api/cuentas/{id}/suspender/**").hasAuthority(AuthotityConstant._ADMIN)
                        .requestMatchers("/api/viajes/reportes/monopatines-mas-viajes/**").hasAuthority(AuthotityConstant._ADMIN)
                        .requestMatchers("/api/pagos/reporte-facturacion/**").hasAuthority(AuthotityConstant._ADMIN)
                        .requestMatchers("/api/viajes/reportes/viajes-de-usuarios/**").hasAuthority(AuthotityConstant._ADMIN)
                        //Falta el actualizarPrecio (4f)
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(new JwtFilter(this.tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}