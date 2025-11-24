package com.tudai.arquitecturasweb.microserviciopago.controller;

import com.tudai.arquitecturasweb.microserviciopago.entity.Tarifa;
import com.tudai.arquitecturasweb.microserviciopago.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {
    @Autowired
    TarifaService tarifaService;

    @GetMapping
    public List<Tarifa> getAll(){
        return tarifaService.getAll();
    }

    @GetMapping("/{id}")
    public Tarifa getById(@PathVariable Long id){
        return tarifaService.getById(id);
    }

    @PostMapping
    public void create(@RequestBody Tarifa t){
        tarifaService.save(t);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Tarifa t, @PathVariable Long id){
        tarifaService.update(id, t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        tarifaService.delete(id);
    }
}
