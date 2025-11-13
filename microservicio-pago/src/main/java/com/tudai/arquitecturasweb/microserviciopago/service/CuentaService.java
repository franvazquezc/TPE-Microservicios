package com.tudai.arquitecturasweb.microserviciopago.service;

import com.tudai.arquitecturasweb.microserviciopago.entity.Cuenta;
import com.tudai.arquitecturasweb.microserviciopago.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {

    @Autowired
    CuentaRepository cuentaRepository;

    public List<Cuenta> getAll() {
        return cuentaRepository.findAll();
    }

    public Cuenta getById(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    public Cuenta save(Cuenta c) {
        return cuentaRepository.save(c);
    }

    public void delete(Long id) {
        cuentaRepository.deleteById(id);
    }

    public void update(Long id, Cuenta nuevo) {
        Cuenta c = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        c.setSaldo(nuevo.getSaldo());
        c.setFechaAlta(nuevo.getFechaAlta());
        c.setTipo(nuevo.getTipo());
        c.setKmMensualesConsumidos(nuevo.getKmMensualesConsumidos());

        cuentaRepository.save(c);
    }
}