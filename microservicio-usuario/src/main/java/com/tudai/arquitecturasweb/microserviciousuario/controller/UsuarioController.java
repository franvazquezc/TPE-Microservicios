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
        return usuarioService.getAll();
    }

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable int id){
        return usuarioService.getById(id);
    }

    @PostMapping
    public void save(@RequestBody Usuario usuario){
        usuarioService.save(usuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        usuarioService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Usuario nuevo, @PathVariable int id){
        usuarioService.update(id, nuevo);
    }
}
