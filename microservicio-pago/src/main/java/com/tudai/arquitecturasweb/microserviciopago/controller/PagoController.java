package com.tudai.arquitecturasweb.microserviciopago.controller;

import com.tudai.arquitecturasweb.microserviciopago.entity.Pago;
import com.tudai.arquitecturasweb.microserviciopago.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    PagoService pagoService;

    @GetMapping
    public List<Pago> getPagos(){
        return pagoService.getAll();
    }

    @GetMapping("/{id}")
    public Pago getPagoById(@PathVariable Long id){
        return pagoService.getById(id);
    }

    @PostMapping
    public void createPago(@RequestBody Pago p){
        pagoService.save(p);
    }

    @PostMapping("/{id}")
    public void update(@RequestBody Pago p, @PathVariable Long id){
        pagoService.update(id, p);
    }

    @DeleteMapping("/{id}")
    public void deletePago(@PathVariable Long id){
        pagoService.delete(id);
    }
}