package com.tudai.arquitecturasweb.microservicioviaje.service;

import com.tudai.arquitecturasweb.microservicioviaje.dto.ViajesMonopatinDTO;
import com.tudai.arquitecturasweb.microservicioviaje.dto.ViajesUsuarioDTO;
import com.tudai.arquitecturasweb.microservicioviaje.entity.Viaje;
import com.tudai.arquitecturasweb.microservicioviaje.feignClient.CuentaFeignClient;
import com.tudai.arquitecturasweb.microservicioviaje.model.TipoCuenta;
import com.tudai.arquitecturasweb.microservicioviaje.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private CuentaFeignClient cuentaFeignClient;


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

    public List<ViajesMonopatinDTO> getMonopatinesConMasDeXViajesEnAnio(int cantViajes, int anio) {
        return this.viajeRepository.getMonopatinesConMasDeXViajesEnAnio(cantViajes, anio);
    }

    public List<Long> getUsuariosByTipoCuenta(TipoCuenta tipoCuenta) {
        return this.cuentaFeignClient.getIdUsuariosByTipoCuenta(tipoCuenta);
    }

    public List<ViajesUsuarioDTO> getUsuariosMasActivos(TipoCuenta  tipoCuenta, LocalDateTime desde, LocalDateTime hasta) {
        List<Long> listaUsuarios = this.getUsuariosByTipoCuenta(tipoCuenta);
        return this.viajeRepository.getUsuariosMasActivos(listaUsuarios, desde, hasta);
    }
}
