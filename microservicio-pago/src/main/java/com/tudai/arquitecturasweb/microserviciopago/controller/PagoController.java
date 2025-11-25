package com.tudai.arquitecturasweb.microserviciopago.controller;

import com.tudai.arquitecturasweb.microserviciopago.dto.FacturacionDTO;
import com.tudai.arquitecturasweb.microserviciopago.entity.Pago;
import com.tudai.arquitecturasweb.microserviciopago.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    PagoService pagoService;

    @GetMapping
    public List<Pago> getAll(){
        return pagoService.getAll();
    }

    @GetMapping("/{id}")
    public Pago getById(@PathVariable Long id){
        return pagoService.getById(id);
    }

    @PostMapping
    public void create(@RequestBody Pago p){
        pagoService.save(p);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Pago p, @PathVariable Long id){
        pagoService.update(id, p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        pagoService.delete(id);
    }

    @GetMapping("/reporte-facturacion")
    public FacturacionDTO getFacturacionByFecha(@RequestParam int anio,
                                                @RequestParam int desdeMes,
                                                @RequestParam int hastaMes) {
        return this.pagoService.getFacturacionByFecha(anio, desdeMes, hastaMes);
    }
}