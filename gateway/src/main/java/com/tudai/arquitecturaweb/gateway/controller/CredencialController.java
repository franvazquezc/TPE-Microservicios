package com.tudai.arquitecturaweb.gateway.controller;

import com.tudai.arquitecturaweb.gateway.dto.CredencialDTO;
import com.tudai.arquitecturaweb.gateway.service.CredencialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credenciales")
@RequiredArgsConstructor
public class CredencialController {

    private final CredencialService credencialService;

    @PostMapping
    public ResponseEntity<?> saveCredencial(@RequestBody @Valid CredencialDTO credencialDTO) {
        final var id = credencialService.saveCredencial( credencialDTO );
        return new ResponseEntity<>( id, HttpStatus.CREATED );
    }

}
