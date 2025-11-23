package com.tudai.arquitecturasweb.microserviciomonopatin.service;

import com.tudai.arquitecturasweb.microserviciomonopatin.dto.KmMonopatinDTO;
import com.tudai.arquitecturasweb.microserviciomonopatin.entity.Monopatin;
import com.tudai.arquitecturasweb.microserviciomonopatin.feignClients.ParadaFeignClient;
import com.tudai.arquitecturasweb.microserviciomonopatin.model.EstadoMonopatin;
import com.tudai.arquitecturasweb.microserviciomonopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MonopatinService {

    @Autowired
    private MonopatinRepository monopatinRepository;

    @Autowired
    private ParadaFeignClient paradaFeignClient;

    public List<Monopatin> getAll() {
        return this.monopatinRepository.findAll();
    }

    public Monopatin getById(Long id) {
        return this.monopatinRepository.findById(id).orElse(null);
    }

    public Monopatin save(Monopatin monopatin) {
        return this.monopatinRepository.save(monopatin);
    }

    @Transactional
    public void update(Monopatin nuevo, Long id) {
        Monopatin m = this.monopatinRepository.findById(id).orElseThrow(()-> new RuntimeException(
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

        this.monopatinRepository.save(m);
    }

    public void delete(Long id) {
        this.monopatinRepository.deleteById(id);
    }

    public List<KmMonopatinDTO> getReporteKmMonopatines() {
        return this.monopatinRepository.getKmMonopatines();
    }

    public List<Monopatin> getMonopatinesCercanos(double latitud, double longitud, double radio) {
        List<Long> idParadas = paradaFeignClient.getParadasCercanas(latitud, longitud, radio);
        return this.monopatinRepository.findByIdParadaActualInAndActivoTrueAndEstado(idParadas, EstadoMonopatin.ESTACIONADO);
    }

    @Transactional
    public void setEstadoCancelado(Long id) {
        Monopatin m = this.getById(id);

        m.setEstado(EstadoMonopatin.CANCELADO);

        this.save(m);
    }
}
