package com.tudai.arquitecturasweb.microservicioviaje.feignClient;

import com.tudai.arquitecturasweb.microservicioviaje.model.Cuenta;
import com.tudai.arquitecturasweb.microservicioviaje.model.TipoCuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservicio-pago", url="http://localhost:8002/cuentas")
public interface CuentaFeignClient {
    @GetMapping
    List<Cuenta> getAll();

    @GetMapping("/{id}")
    Cuenta getById(@PathVariable Long id);

    @PostMapping
    void create(@RequestBody Cuenta c);

    @PutMapping("/{id}")
    void update(@RequestBody Cuenta c, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

    @GetMapping("/usuarios-by-cuenta/{tipoCuenta}")
    List<Integer> getIdUsuariosByTipoCuenta(@PathVariable("tipoCuenta") TipoCuenta tipoCuenta);
}
