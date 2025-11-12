package com.tudai.arquitecturasweb.microserviciousuario.service;

import com.tudai.arquitecturasweb.microserviciousuario.entity.Usuario;
import com.tudai.arquitecturasweb.microserviciousuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Usuario getById(int id){
        return usuarioRepository.findById(id).orElseThrow(()->new RuntimeException("Usuario no encontrado"));
    }

    public void update(int id,Usuario nuevo){
        Usuario u = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException(
                "Usuario no encontrado"
        ));

        u.setNombre(nuevo.getNombre());
        u.setApellido(nuevo.getApellido());
        u.setEmail(nuevo.getEmail());
        u.setAlias(nuevo.getAlias());
        u.setCelular(nuevo.getCelular());

        usuarioRepository.save(u);
    }

    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public void delete(int id){
        usuarioRepository.deleteById(id);
    }
}
