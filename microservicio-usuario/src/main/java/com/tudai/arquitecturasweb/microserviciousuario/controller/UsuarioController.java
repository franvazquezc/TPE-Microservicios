package com.tudai.arquitecturasweb.microserviciousuario.controller;

import com.tudai.arquitecturasweb.microserviciousuario.dto.ViajesUsuarioDTO;
import com.tudai.arquitecturasweb.microserviciousuario.entity.Usuario;
import com.tudai.arquitecturasweb.microserviciousuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/{id}/viajes")
    public ViajesUsuarioDTO getUsoUsuario(@PathVariable int idUsuario,
                                          @RequestParam LocalDateTime desde,
                                          @RequestParam LocalDateTime hasta) {
        return this.usuarioService.getViajesUsuario(idUsuario, desde, hasta);
    }

    @GetMapping("/cuenta/{id}/viajes")
    public List<ViajesUsuarioDTO> getUsoUsuariosDeCuenta(@PathVariable Long idCuenta,
                                                        @RequestParam LocalDateTime desde,
                                                        @RequestParam LocalDateTime hasta) {
        return this.usuarioService.getViajesUsuariosDeCuenta(idCuenta, desde, hasta);
    }
}
