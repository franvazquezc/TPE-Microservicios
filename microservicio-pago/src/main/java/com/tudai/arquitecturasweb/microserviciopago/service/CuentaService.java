package com.tudai.arquitecturasweb.microserviciopago.service;

import com.tudai.arquitecturasweb.microserviciopago.entity.Cuenta;
import com.tudai.arquitecturasweb.microserviciopago.model.TipoCuenta;
import com.tudai.arquitecturasweb.microserviciopago.repository.CuentaRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void update(Long id, Cuenta nuevo) {
        Cuenta c = this.cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        c.setTipo(nuevo.getTipo());
        c.setFechaAlta(nuevo.getFechaAlta());
        c.setSaldo(nuevo.getSaldo());
        c.setKmMensualesDisponibles(nuevo.getKmMensualesDisponibles());
        c.setIdUsuarios(nuevo.getIdUsuarios());

        this.cuentaRepository.save(c);
    }

    @Transactional
    public void suspenderCuenta(Long id) {
        Cuenta c = this.cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        c.setTipo(TipoCuenta.ANULADA);

        this.cuentaRepository.save(c);
    }

    @Transactional
    public List<Integer> getIdUsuariosByTipoCuenta(TipoCuenta tipoCuenta) {
        List<List<Integer>> listasDeUsuarios = this.cuentaRepository.getIdUsuariosByTipoCuenta(tipoCuenta);
        Set<Integer> usuariosUnicos = new HashSet<>();

        for (List<Integer> listaUsuarios : listasDeUsuarios) {
            for (Integer usuarioId : listaUsuarios) {
                usuariosUnicos.add(usuarioId);
            }
        }

        return new ArrayList<>(usuariosUnicos);
    }

    @Transactional
    public boolean cobrar(double monto, Long id) {
        Cuenta c = this.cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        double nuevoSaldo = c.getSaldo() - monto;
        if (nuevoSaldo >= 0) {
            c.setSaldo(nuevoSaldo);
            this.cuentaRepository.save(c);
            return true;
        } else {
            return false;
        }
    }

    public List<Integer> getUsuariosByCuenta(Long idCuenta) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return cuenta.getIdUsuarios();
    }
}