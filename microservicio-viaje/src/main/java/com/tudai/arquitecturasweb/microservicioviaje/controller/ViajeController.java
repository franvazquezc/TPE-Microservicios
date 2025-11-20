package com.tudai.arquitecturasweb.microservicioviaje.controller;

import com.tudai.arquitecturasweb.microservicioviaje.dto.ViajesMonopatinDTO;
import com.tudai.arquitecturasweb.microservicioviaje.dto.ViajesUsuarioDTO;
import com.tudai.arquitecturasweb.microservicioviaje.entity.Viaje;
import com.tudai.arquitecturasweb.microservicioviaje.model.TipoCuenta;
import com.tudai.arquitecturasweb.microservicioviaje.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    ViajeService viajeService;

    @GetMapping
    public List<Viaje> getAll() {
        return viajeService.getAll();
    }

    @GetMapping("/{id}")
    public Viaje getById(@PathVariable("id") Long id) {
        return viajeService.getById(id);
    }

    @PostMapping
    public void create(@RequestBody Viaje v) {
        viajeService.save(v);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Viaje v) {
        viajeService.update(v, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        viajeService.delete(id);
    }

    @GetMapping("/reportes/monopatines-mas-viajes")
    public List<ViajesMonopatinDTO> getMonopatinesConMasDeXViajes(@RequestParam int cantidadMinima,
                                                                  @RequestParam int anio) {
        return this.viajeService.getMonopatinesConMasDeXViajesEnAnio(cantidadMinima, anio);
    }

    @GetMapping("/reportes/viajes-de-usuarios")
    public List<ViajesUsuarioDTO> getUsuariosMasActivos(@RequestParam TipoCuenta tipoCuenta,
                                                        @RequestParam LocalDateTime desde,
                                                        @RequestParam LocalDateTime hasta) {
        return this.viajeService.getUsuariosMasActivos(tipoCuenta, desde, hasta);
    }

    @GetMapping("/activos")
    public List<Long> getIdViajesActivos() {
        return this.viajeService.getIdViajesActivos();
    }

    @PutMapping("/{id}/concluir")
    public void concluirViaje(@PathVariable Long id) {
        this.viajeService.concluirViaje(id);
    }
}
