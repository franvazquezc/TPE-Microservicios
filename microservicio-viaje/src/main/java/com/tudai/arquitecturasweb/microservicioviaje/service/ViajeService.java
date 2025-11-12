package com.tudai.arquitecturasweb.microservicioviaje.service;

import com.tudai.arquitecturasweb.microservicioviaje.entity.Viaje;
import com.tudai.arquitecturasweb.microservicioviaje.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    public List<Viaje> getAll() {
        return viajeRepository.findAll();
    }

    public Viaje getById(Long id) {
        return viajeRepository.findById(id).orElse(null);
    }

    public Viaje save(Viaje monopatin) {
        return viajeRepository.save(monopatin);
    }

    public void update(Viaje nuevo, Long id) {
        Viaje v = viajeRepository.findById(id).orElseThrow(()-> new RuntimeException(
                "Viaje no encontrado"
        ));

        v.setFechaInicio(nuevo.getFechaInicio());
        v.setFechaFin(nuevo.getFechaFin());
        v.setMinutosDePausa(nuevo.getMinutosDePausa());
        v.setKmRecorridos(nuevo.getKmRecorridos());
        v.setIdCuenta(nuevo.getIdCuenta());
        v.setIdUsuario(nuevo.getIdUsuario());
        v.setIdMonopatin(nuevo.getIdMonopatin());
        v.setIdParadaInicial(nuevo.getIdParadaInicial());
        v.setIdParadaFinal(nuevo.getIdParadaFinal());

        viajeRepository.save(v);
    }

    public void deleteById(Long id) {
        viajeRepository.deleteById(id);
    }
}
