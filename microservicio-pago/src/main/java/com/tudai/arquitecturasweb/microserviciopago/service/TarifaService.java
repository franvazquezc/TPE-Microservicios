package com.tudai.arquitecturasweb.microserviciopago.service;

import com.tudai.arquitecturasweb.microserviciopago.entity.Tarifa;
import com.tudai.arquitecturasweb.microserviciopago.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarifaService {
    @Autowired
    TarifaRepository tarifaRepository;

    public List<Tarifa> getAll() {
        return tarifaRepository.findAll();
    }

    public Tarifa getById(Long id) {
        return tarifaRepository.findById(id).orElse(null);
    }

    public Tarifa save(Tarifa t) {
        return tarifaRepository.save(t);
    }

    public void delete(Long id) {
        tarifaRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Tarifa nueva) {
        Tarifa t = tarifaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarifa no encontrada"));

        t.setDesde(nueva.getDesde());
        t.setHasta(nueva.getHasta());
        t.setTarifaXMinPlana(nueva.getTarifaXMinPlana());
        t.setTarifaXMinPremium(nueva.getTarifaXMinPremium());
        t.setTarifaXMesPremium(nueva.getTarifaXMesPremium());
        t.setTarifaXMinExtra(nueva.getTarifaXMinExtra());

        tarifaRepository.save(t);
    }

    public Tarifa getTarifaByFecha(LocalDate fecha) {
        return this.tarifaRepository.getTarifaByFecha(fecha);
    }
}