package com.tudai.arquitecturasweb.microservicioviaje.controller;

import com.tudai.arquitecturasweb.microservicioviaje.entity.Viaje;
import com.tudai.arquitecturasweb.microservicioviaje.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        viajeService.deleteById(id);
    }
}
