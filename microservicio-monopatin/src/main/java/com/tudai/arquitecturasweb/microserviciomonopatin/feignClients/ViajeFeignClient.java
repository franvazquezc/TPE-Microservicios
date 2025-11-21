package com.tudai.arquitecturasweb.microserviciomonopatin.feignClients;

import com.tudai.arquitecturasweb.microserviciomonopatin.model.Viaje;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservicio-viaje", url="http://localhost:8005/viajes")
public interface ViajeFeignClient {

    @GetMapping
    List<Viaje> getAll();

    @GetMapping("/{id}")
    Viaje getById(@PathVariable("id") Long id);

    @PostMapping
    void create(@RequestBody Viaje v);

    @PutMapping("/{id}")
    void update(@PathVariable Long id, @RequestBody Viaje v);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

    @GetMapping("/activos")
    List<Long> getIdViajesActivos();
}
