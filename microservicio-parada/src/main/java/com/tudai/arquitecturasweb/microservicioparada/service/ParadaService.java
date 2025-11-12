package com.tudai.arquitecturasweb.microservicioparada.service;

import com.tudai.arquitecturasweb.microservicioparada.entity.Parada;
import com.tudai.arquitecturasweb.microservicioparada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParadaService {

    @Autowired
    private ParadaRepository paradaRepository;

    public List<Parada> getAll() {
        return paradaRepository.findAll();
    }

    public Parada getById(Long id) {
        return paradaRepository.findById(id).orElse(null);
    }

    public Parada save(Parada monopatin) {
        return paradaRepository.save(monopatin);
    }

    public void update(Parada nueva, Long id) {
        Parada p = paradaRepository.findById(id).orElseThrow(()-> new RuntimeException(
                "Parada no encontrada"
        ));

        p.setDireccion(nueva.getDireccion());
        p.setLatitud(nueva.getLatitud());
        p.setLongitud(nueva.getLongitud());

        paradaRepository.save(p);
    }

    public void deleteById(Long id) {
        paradaRepository.deleteById(id);
    }
}
