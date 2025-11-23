package com.tudai.arquitecturasweb.microserviciopago.feignClients;

import com.tudai.arquitecturasweb.microserviciopago.model.Viaje;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservicio-viaje", url="http://microservicio-viaje:8005/viajes")
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

    @PutMapping("/{id}/concluir")
    void concluirViaje(@PathVariable Long id);
}
