package com.tudai.arquitecturasweb.microserviciopago.controller;

import com.tudai.arquitecturasweb.microserviciopago.entity.Cuenta;
import com.tudai.arquitecturasweb.microserviciopago.model.TipoCuenta;
import com.tudai.arquitecturasweb.microserviciopago.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> getPagos(){
        return cuentaService.getAll();
    }

    @GetMapping("/{id}")
    public Cuenta getPagoById(@PathVariable Long id){
        return cuentaService.getById(id);
    }

    @PostMapping
    public void createPago(@RequestBody Cuenta c){
            cuentaService.save(c);
    }

    @PostMapping("/{id}")
    public void update(@RequestBody Cuenta c, @PathVariable Long id){
        cuentaService.update(id, c);
    }

    @DeleteMapping("/{id}")
    public void deletePago(@PathVariable Long id) {
        cuentaService.delete(id);
    }

    @PostMapping("/{id}/suspender")
    public void suspenderCuenta(@PathVariable Long id){
        this.cuentaService.suspenderCuenta(id);
    }

    @GetMapping("/usuarios-by-cuenta/{tipoCuenta}")
    public List<Long> getIdUsuariosByTipoCuenta(@PathVariable("tipoCuenta") TipoCuenta tipoCuenta) {
        return this.cuentaService.getIdUsuariosByTipoCuenta(tipoCuenta);
    }
}