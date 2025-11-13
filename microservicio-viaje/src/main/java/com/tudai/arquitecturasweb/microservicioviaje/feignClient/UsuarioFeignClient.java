package com.tudai.arquitecturasweb.microservicioviaje.feignClient;

import com.tudai.arquitecturasweb.microservicioviaje.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservicio-usuario", url="http://localhost:8003/usuarios")
public interface UsuarioFeignClient {
    @GetMapping
    List<Usuario> getAll();

    @GetMapping("/{id}")
    Usuario getById(@PathVariable int id);

    @PostMapping
    void save(@RequestBody Usuario usuario);

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id);

    @PutMapping("/{id}")
    void update(@RequestBody Usuario nuevo, @PathVariable int id);
}
