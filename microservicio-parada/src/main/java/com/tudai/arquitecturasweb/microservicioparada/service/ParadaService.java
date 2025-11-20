package com.tudai.arquitecturasweb.microservicioparada.service;

import com.tudai.arquitecturasweb.microservicioparada.entity.Parada;
import com.tudai.arquitecturasweb.microservicioparada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParadaService {

    @Autowired
    private ParadaRepository paradaRepository;

    public List<Parada> getAll() {
        return this.paradaRepository.findAll();
    }

    public Parada getById(Long id) {
        return this.paradaRepository.findById(id).orElse(null);
    }

    public Parada save(Parada monopatin) {
        return this.paradaRepository.save(monopatin);
    }

    @Transactional
    public void update(Parada nueva, Long id) {
        Parada p = this.paradaRepository.findById(id).orElseThrow(()-> new RuntimeException(
                "Parada no encontrada"
        ));

        p.setDireccion(nueva.getDireccion());
        p.setLatitud(nueva.getLatitud());
        p.setLongitud(nueva.getLongitud());

        this.paradaRepository.save(p);
    }

    public void delete(Long id) {
        this.paradaRepository.deleteById(id);
    }


    public List<Long> getParadasCercanas(double latitud, double longitud, double radioKm) {
        List<Parada> todas = this.getAll();
        List<Long> idsCercanos = new ArrayList<>();

        for (Parada p : todas) {
            double distancia = calcularDistancia(latitud, longitud, p.getLatitud(), p.getLongitud());
            if (distancia <= radioKm) {
                idsCercanos.add(p.getId());
            }
        }
        return idsCercanos;
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la Tierra en km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // distancia en km
    }
}
