package com.tudai.arquitecturasweb.microserviciousuario.controller;

import com.tudai.arquitecturasweb.microserviciousuario.entity.Usuario;
import com.tudai.arquitecturasweb.microserviciousuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAll(){
        return this.usuarioService.getAll();
    }

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable int id){
        return this.usuarioService.getById(id);
    }

    @PostMapping
    public void save(@RequestBody Usuario usuario){
        this.usuarioService.save(usuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        this.usuarioService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Usuario nuevo, @PathVariable int id){
        this.usuarioService.update(id, nuevo);
    }
}
