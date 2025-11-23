package com.tudai.arquitecturasweb.microservicioviaje.service;

import com.tudai.arquitecturasweb.microservicioviaje.dto.ViajesMonopatinDTO;
import com.tudai.arquitecturasweb.microservicioviaje.dto.ViajesUsuarioDTO;
import com.tudai.arquitecturasweb.microservicioviaje.entity.Viaje;
import com.tudai.arquitecturasweb.microservicioviaje.feignClient.CuentaFeignClient;
import com.tudai.arquitecturasweb.microservicioviaje.feignClient.MonopatinFeignClient;
import com.tudai.arquitecturasweb.microservicioviaje.model.TipoCuenta;
import com.tudai.arquitecturasweb.microservicioviaje.repository.ViajeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private CuentaFeignClient cuentaFeignClient;

    @Autowired
    private MonopatinFeignClient monopatinFeignClient;

    public List<Viaje> getAll() {
        return viajeRepository.findAll();
    }

    public Viaje getById(Long id) {
        return viajeRepository.findById(id).orElse(null);
    }

    public Viaje save(Viaje monopatin) {
        return viajeRepository.save(monopatin);
    }

    @Transactional
    public void update(Viaje nuevo, Long id) {
        Viaje v = viajeRepository.findById(id).orElseThrow(()-> new RuntimeException(
                "Viaje no encontrado"
        ));

        v.setFechaInicio(nuevo.getFechaInicio());
        v.setFechaFin(nuevo.getFechaFin());
        v.setMinutosPausas(nuevo.getMinutosPausas());
        v.setKmRecorridos(nuevo.getKmRecorridos());
        v.setActivo(nuevo.isActivo());
        v.setIdUsuario(nuevo.getIdUsuario());
        v.setIdCuenta(nuevo.getIdCuenta());
        v.setIdMonopatin(nuevo.getIdMonopatin());
        v.setIdParadaInicial(nuevo.getIdParadaInicial());
        v.setIdParadaFinal(nuevo.getIdParadaFinal());

        viajeRepository.save(v);
    }

    public void delete(Long id) {
        viajeRepository.deleteById(id);
    }

    public List<ViajesMonopatinDTO> getMonopatinesConMasDeXViajesEnAnio(int cantViajes, int anio) {
        return this.viajeRepository.getMonopatinesConMasDeXViajesEnAnio(cantViajes, anio);
    }

    public List<Integer> getUsuariosByTipoCuenta(TipoCuenta tipoCuenta) {
        return this.cuentaFeignClient.getIdUsuariosByTipoCuenta(tipoCuenta);
    }

    public List<ViajesUsuarioDTO> getUsuariosMasActivos(TipoCuenta  tipoCuenta, Instant desde, Instant hasta) {
        List<Integer> listaUsuarios = this.getUsuariosByTipoCuenta(tipoCuenta);
        return this.viajeRepository.getUsuariosMasActivos(listaUsuarios, desde, hasta);
    }

    public List<Long> getIdViajesActivos() {
        return this.viajeRepository.getIdViajesActivos();
    }

    @Transactional
    public void concluirViaje(Long id) {
        Viaje v = this.getById(id);

        v.setActivo(false);
        v.setFechaFin(Instant.now());

        this.viajeRepository.save(v);

        this.monopatinFeignClient.setEstadoCancelado(v.getIdMonopatin());
    }

    public int getCantidadViajesUsuario(int idUsuario, Instant desde, Instant hasta) {
        return this.viajeRepository.getCantidadViajesUsuario(idUsuario, desde, hasta);
    }
}
