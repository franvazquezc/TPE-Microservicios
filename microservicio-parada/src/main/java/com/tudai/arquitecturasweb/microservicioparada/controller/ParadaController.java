package com.tudai.arquitecturasweb.microservicioparada.controller;

import com.tudai.arquitecturasweb.microservicioparada.entity.Parada;
import com.tudai.arquitecturasweb.microservicioparada.service.ParadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paradas")
public class ParadaController {

    @Autowired
    ParadaService paradaService;

    @GetMapping
    public List<Parada> getAll() {
        return this.paradaService.getAll();
    }

    @GetMapping("/{id}")
    public Parada getById(@PathVariable("id") Long id) {
        return this.paradaService.getById(id);
    }

    @PostMapping
    public void create(@RequestBody Parada p) {
        this.paradaService.save(p);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Parada p) {
        this.paradaService.update(p, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.paradaService.delete(id);
    }

    @GetMapping("/cercanas")
    public List<Long> getParadasCercanas(@RequestParam double latitud,
                                         @RequestParam double longitud,
                                         @RequestParam(defaultValue = "0.5") double radioKm) {
        return this.paradaService.getParadasCercanas(latitud, longitud, radioKm);
    }
}
