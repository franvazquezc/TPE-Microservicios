package com.tudai.arquitecturasweb.microserviciomonopatin.feignClients;

import com.tudai.arquitecturasweb.microserviciomonopatin.model.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservicio-parada", url="http://microservicio-parada:8003/paradas")
public interface ParadaFeignClient {

    @GetMapping
    List<Parada> getAll();

    @GetMapping("/{id}")
    Parada getById(@PathVariable("id") Long id);

    @PostMapping
    void create(@RequestBody Parada p);

    @PutMapping("/{id}")
    void update(@PathVariable Long id, @RequestBody Parada p);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

    @GetMapping("/cercanas")
    List<Long> getParadasCercanas(@RequestParam double latitud,
                                  @RequestParam double longitud,
                                  @RequestParam(defaultValue = "0.5") double radioKm);
}
