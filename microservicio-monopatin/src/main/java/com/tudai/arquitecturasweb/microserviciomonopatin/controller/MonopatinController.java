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
        return monopatinService.getAll();
    }

    @GetMapping("/{id}")
    public Monopatin getById(@PathVariable("id") Long id) {
        return monopatinService.getById(id);
    }

    @PostMapping
    public void create(@RequestBody Monopatin m) {
        monopatinService.save(m);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Monopatin m) {
        monopatinService.update(m, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        monopatinService.delete(id);
    }

    @GetMapping("/reporteKm")
    public List<KmMonopatinDTO> getReporteKmMonopatines() {
        return monopatinService.getReporteKmMonopatines();
    }
}