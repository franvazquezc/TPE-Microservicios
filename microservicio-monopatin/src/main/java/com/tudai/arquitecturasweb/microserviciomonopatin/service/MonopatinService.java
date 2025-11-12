package com.tudai.arquitecturasweb.microserviciomonopatin.service;

import com.tudai.arquitecturasweb.microserviciomonopatin.entity.Monopatin;
import com.tudai.arquitecturasweb.microserviciomonopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonopatinService {

    @Autowired
    private MonopatinRepository monopatinRepository;

    public List<Monopatin> getAll() {
        return monopatinRepository.findAll();
    }

    public Monopatin getById(Long id) {
        return monopatinRepository.findById(id).orElse(null);
    }

    public Monopatin save(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    public void update(Monopatin nuevo, Long id) {
        Monopatin m = monopatinRepository.findById(id).orElseThrow(()-> new RuntimeException(
                "Monopatin no encontrado"
        ));

        m.setActivo(nuevo.isActivo());
        m.setEstado(nuevo.getEstado());
        m.setLatitud(nuevo.getLatitud());
        m.setLongitud(nuevo.getLongitud());
        m.setKmRecorridos(nuevo.getKmRecorridos());
        m.setMinutosDeUso(nuevo.getMinutosDeUso());
        m.setIdParadaActual(nuevo.getIdParadaActual());
        m.setIdViajeActual(nuevo.getIdViajeActual());

        monopatinRepository.save(m);
    }

    public void deleteById(Long id) {
        monopatinRepository.deleteById(id);
    }
}
