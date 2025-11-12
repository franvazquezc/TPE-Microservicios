package com.tudai.arquitecturasweb.microservicioparada.controller;

import com.tudai.arquitecturasweb.microservicioparada.entity.Parada;
import com.tudai.arquitecturasweb.microservicioparada.service.ParadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paradas")
public class ParadaController {

    @Autowired
    ParadaService paradaService;

    @GetMapping
    public List<Parada> getAll() {
        return paradaService.getAll();
    }

    @GetMapping("/{id}")
    public Parada getById(@PathVariable("id") Long id) {
        return paradaService.getById(id);
    }

    @PostMapping
    public void create(@RequestBody Parada p) {
        paradaService.save(p);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Parada p) {
        paradaService.update(p, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paradaService.deleteById(id);
    }
}
