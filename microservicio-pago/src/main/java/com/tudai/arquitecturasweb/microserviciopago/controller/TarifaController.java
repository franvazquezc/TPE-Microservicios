package com.tudai.arquitecturasweb.microserviciopago.controller;

import com.tudai.arquitecturasweb.microserviciopago.entity.Tarifa;
import com.tudai.arquitecturasweb.microserviciopago.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarifa")
public class TarifaController {
    @Autowired
    TarifaService tarifaService;

    @GetMapping
    public List<Tarifa> getPagos(){
        return tarifaService.getAll();
    }

    @GetMapping("/{id}")
    public Tarifa getPagoById(@PathVariable Long id){
        return tarifaService.getById(id);
    }

    @PostMapping
    public void createPago(@RequestBody Tarifa t){
        tarifaService.save(t);
    }

    @PostMapping("/{id}")
    public void update(@RequestBody Tarifa t, @PathVariable Long id){
        tarifaService.update(id, t);
    }

    @DeleteMapping("/{id}")
    public void deletePago(@PathVariable Long id){
        tarifaService.delete(id);
    }
}
