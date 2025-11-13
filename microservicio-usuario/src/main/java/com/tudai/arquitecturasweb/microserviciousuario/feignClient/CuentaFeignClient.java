package com.tudai.arquitecturasweb.microserviciousuario.feignClient;

import com.tudai.arquitecturasweb.microserviciousuario.model.Cuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservicio-pago", url="http://localhost:8003/cuentas")
public interface CuentaFeignClient {
    @GetMapping
    List<Cuenta> getPagos();

    @GetMapping("/{id}")
    Cuenta getPagoById(@PathVariable Long id);

    @PostMapping
    void createPago(@RequestBody Cuenta c);

    @PostMapping("/{id}")
    void update(@RequestBody Cuenta c, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void deletePago(@PathVariable Long id);
}
