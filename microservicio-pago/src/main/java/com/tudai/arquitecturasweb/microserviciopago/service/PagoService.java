package com.tudai.arquitecturasweb.microserviciopago.service;

import com.tudai.arquitecturasweb.microserviciopago.dto.FacturacionDTO;
import com.tudai.arquitecturasweb.microserviciopago.entity.Cuenta;
import com.tudai.arquitecturasweb.microserviciopago.entity.Pago;
import com.tudai.arquitecturasweb.microserviciopago.entity.Tarifa;
import com.tudai.arquitecturasweb.microserviciopago.feignClients.ViajeFeignClient;
import com.tudai.arquitecturasweb.microserviciopago.model.TipoCuenta;
import com.tudai.arquitecturasweb.microserviciopago.model.Viaje;
import com.tudai.arquitecturasweb.microserviciopago.repository.PagoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private TarifaService tarifaService;
    @Autowired
    private ViajeFeignClient viajeFeignClient;

    public List<Pago> getAll() {
        return pagoRepository.findAll();
    }

    public Pago getById(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void delete(Long id) {
        pagoRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Pago nuevo) {
        Pago p = pagoRepository.findById(id).orElseThrow(() -> new RuntimeException("pago no encontrado"));

        p.setFecha(nuevo.getFecha());
        p.setMontoFacturado(nuevo.getMontoFacturado());
        p.setKmFacturados(nuevo.getKmFacturados());

        pagoRepository.save(p);
    }

    public FacturacionDTO getFacturacionByFecha(int anio, int desdeMes, int hastaMes) {
        return this.pagoRepository.getFacturacionByFecha(anio, desdeMes, hastaMes);
    }

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void procesarPagosEnViajesActivos() {
        List<Long> idViajesActivos = this.viajeFeignClient.getIdViajesActivos();
        for(Long id: idViajesActivos) {
            Viaje v = this.viajeFeignClient.getById(id);
            this.determinarPago(v);
        }
    }

    @Transactional
    public void determinarPago(Viaje v) {
        Tarifa t = this.tarifaService.getTarifaByFecha(LocalDate.now());
        Cuenta c = this.cuentaService.getById(v.getIdCuenta());
        Pago p = this.getById(v.getId());

        // Si no existe crea un pago.
        if(p == null) {
            p = new Pago(v.getId(), v.getIdCuenta(), LocalDateTime.now(), 0.0, 0.0);
            this.save(p);
        }

        if(c.getTipo() == TipoCuenta.BASICA) {
            if(v.getMinTotalesDePausa() < 15) {
                this.procesarPago(t.getTarifaXMinPlana(), p);
            } else {
                this.procesarPago(t.getTarifaXMinExtra(), p);
            }
        }

        if(c.getTipo() == TipoCuenta.PREMIUM) {
            if(c.getKmMensualesDisponibles() > this.getKmNoFacturados(p)) {
                c.setKmMensualesDisponibles(c.getKmMensualesDisponibles() - this.getKmNoFacturados(p));
                this.cuentaService.update(c.getId(), c);
            } else {
                if(v.getMinTotalesDePausa() < 15) {
                    this.procesarPago(t.getTarifaXMinPremium(), p);
                } else {
                    this.procesarPago(t.getTarifaXMinExtra(), p);
                }
            }
        }
    }

    @Transactional
    public void procesarPago(double tarifa, Pago p) {
        double montoPagoActual = tarifa * this.getMinDesdeUltimaFacturacion(p);
        boolean cobroExitoso = this.cuentaService.cobrar(montoPagoActual, p.getIdCuenta());
        if(cobroExitoso) {
            p.setMontoFacturado(p.getMontoFacturado() + montoPagoActual);
            p.setKmFacturados(p.getKmFacturados() + this.getKmNoFacturados(p));
            p.setFecha(LocalDateTime.now());

            this.save(p);
        } else {
            this.rechazarPago(p.getIdViaje());
        }
    }

    public void rechazarPago(Long idViaje) {
        this.viajeFeignClient.concluirViaje(idViaje);
    }

    public long getMinDesdeUltimaFacturacion(Pago p) {
        LocalDateTime referencia = (p.getFecha() != null) ? p.getFecha() : this.viajeFeignClient.getById(p.getIdViaje()).getFechaInicio();
        return Duration.between(referencia, LocalDateTime.now()).toMinutes();
    }

    public Double getKmNoFacturados(Pago p) {
        return this.viajeFeignClient.getById(p.getIdViaje()).getKmRecorridos() - p.getKmFacturados();
    }
}