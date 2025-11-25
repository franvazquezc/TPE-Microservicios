package com.tudai.arquitecturasweb.microserviciomonopatin.controller;

import com.tudai.arquitecturasweb.microserviciomonopatin.dto.KmMonopatinDTO;
import com.tudai.arquitecturasweb.microserviciomonopatin.entity.Monopatin;
import com.tudai.arquitecturasweb.microserviciomonopatin.service.MonopatinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    @Autowired
    MonopatinService monopatinService;

    @GetMapping
    public List<Monopatin> getAll() {
        return this.monopatinService.getAll();
    }

    @GetMapping("/{id}")
    public Monopatin getById(@PathVariable("id") Long id) {
        return this.monopatinService.getById(id);
    }

    @PostMapping
    public void create(@RequestBody Monopatin m) {
        this.monopatinService.save(m);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Monopatin m) {
        this.monopatinService.update(m, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.monopatinService.delete(id);
    }

    @GetMapping("/reporte-km")
    public List<KmMonopatinDTO> getReporteKmMonopatines() {
        return this.monopatinService.getReporteKmMonopatines();
    }

    @GetMapping("/cercanos")
    public List<Monopatin> getMonopatinesCercanos(@RequestParam double latitud,
                                                  @RequestParam double longitud,
                                                  @RequestParam(defaultValue = "0.5") double radio) {
        return this.monopatinService.getMonopatinesCercanos(latitud, longitud, radio);
    }

    @PutMapping("/{id}/cancelar")
    public void setEstadoCancelado(@PathVariable Long id){
        this.monopatinService.setEstadoCancelado(id);
    }
}