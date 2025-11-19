package com.tudai.arquitecturasweb.microserviciopago.service;

import com.tudai.arquitecturasweb.microserviciopago.entity.Cuenta;
import com.tudai.arquitecturasweb.microserviciopago.model.TipoCuenta;
import com.tudai.arquitecturasweb.microserviciopago.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CuentaService {

    @Autowired
    CuentaRepository cuentaRepository;

    public List<Cuenta> getAll() {
        return this.cuentaRepository.findAll();
    }

    public Cuenta getById(Long id) {
        return this.cuentaRepository.findById(id).orElse(null);
    }

    public Cuenta save(Cuenta c) {
        return this.cuentaRepository.save(c);
    }

    public void delete(Long id) {
        this.cuentaRepository.deleteById(id);
    }

    public void update(Long id, Cuenta nuevo) {
        Cuenta c = this.cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        c.setSaldo(nuevo.getSaldo());
        c.setFechaAlta(nuevo.getFechaAlta());
        c.setTipo(nuevo.getTipo());
        c.setKmMensualesConsumidos(nuevo.getKmMensualesConsumidos());

        this.cuentaRepository.save(c);
    }

    public void suspenderCuenta(Long id) {
        Cuenta c = this.cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        c.setTipo(TipoCuenta.ANULADA);

        this.cuentaRepository.save(c);
    }

    public List<Long> getIdUsuariosByTipoCuenta(TipoCuenta tipoCuenta) {
        List<List<Long>> listasDeUsuarios = this.cuentaRepository.getIdUsuariosByTipoCuenta(tipoCuenta);
        Set<Long> usuariosUnicos = new HashSet<>();

        for (List<Long> listaUsuarios : listasDeUsuarios) {
            for (Long usuarioId : listaUsuarios) {
                usuariosUnicos.add(usuarioId);
            }
        }

        return new ArrayList<>(usuariosUnicos);
    }
}