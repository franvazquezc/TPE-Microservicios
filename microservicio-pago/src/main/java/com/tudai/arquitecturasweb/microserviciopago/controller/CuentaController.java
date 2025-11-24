package com.tudai.arquitecturasweb.microserviciopago.controller;

import com.tudai.arquitecturasweb.microserviciopago.entity.Cuenta;
import com.tudai.arquitecturasweb.microserviciopago.model.TipoCuenta;
import com.tudai.arquitecturasweb.microserviciopago.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> getAll(){
        return cuentaService.getAll();
    }

    @GetMapping("/{id}")
    public Cuenta getById(@PathVariable Long id){
        return cuentaService.getById(id);
    }

    @PostMapping
    public void create(@RequestBody Cuenta c){
            cuentaService.save(c);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Cuenta c, @PathVariable Long id){
        cuentaService.update(id, c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cuentaService.delete(id);
    }

    @PutMapping("/{id}/suspender")
    public void suspenderCuenta(@PathVariable Long id){
        this.cuentaService.suspenderCuenta(id);
    }

    @GetMapping("/usuarios-by-cuenta/{tipoCuenta}")
    public List<Integer> getIdUsuariosByTipoCuenta(@PathVariable("tipoCuenta") TipoCuenta tipoCuenta) {
        return this.cuentaService.getIdUsuariosByTipoCuenta(tipoCuenta);
    }

    @GetMapping("/{id}/usuarios")
    public List<Integer> getUsuariosByCuenta(@PathVariable("id") Long idCuenta) {
        return this.cuentaService.getUsuariosByCuenta(idCuenta);
    }
}