package com.tudai.arquitecturasweb.microservicioviaje.feignClient;

import com.tudai.arquitecturasweb.microservicioviaje.model.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservicio-monopatin", url="http://localhost:8001/monopatines")
public interface MonopatinFeignClient {

    @GetMapping
    List<Monopatin> getAll();

    @GetMapping("/{id}")
    Monopatin getById(@PathVariable("id") Long id);

    @PostMapping
    void create(@RequestBody Monopatin m);

    @PutMapping("/{id}")
    void update(@PathVariable Long id, @RequestBody Monopatin m);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
