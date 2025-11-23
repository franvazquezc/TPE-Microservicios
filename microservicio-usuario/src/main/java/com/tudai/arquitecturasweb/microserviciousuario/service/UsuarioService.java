package com.tudai.arquitecturasweb.microserviciousuario.service;

import com.tudai.arquitecturasweb.microserviciousuario.dto.ViajesUsuarioDTO;
import com.tudai.arquitecturasweb.microserviciousuario.entity.Usuario;
import com.tudai.arquitecturasweb.microserviciousuario.feignClient.CuentaFeignClient;
import com.tudai.arquitecturasweb.microserviciousuario.feignClient.ViajeFeignClient;
import com.tudai.arquitecturasweb.microserviciousuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ViajeFeignClient viajeFeignClient;
    @Autowired
    private CuentaFeignClient cuentaFeignClient;

    public List<Usuario> getAll(){
        return this.usuarioRepository.findAll();
    }

    public Usuario getById(int id){
        return this.usuarioRepository.findById(id).orElseThrow(()->new RuntimeException("Usuario no encontrado"));
    }

    @Transactional
    public void update(int id, Usuario nuevo){
        Usuario u = this.usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException(
                "Usuario no encontrado"
        ));

        u.setNombre(nuevo.getNombre());
        u.setApellido(nuevo.getApellido());
        u.setEmail(nuevo.getEmail());
        u.setAlias(nuevo.getAlias());
        u.setCelular(nuevo.getCelular());

        this.usuarioRepository.save(u);
    }

    public void save(Usuario usuario){
        this.usuarioRepository.save(usuario);
    }

    public void delete(int id){
        this.usuarioRepository.deleteById(id);
    }

    public ViajesUsuarioDTO getViajesUsuario(int id, Instant desde, Instant hasta) {
        int cantidad = this.viajeFeignClient.getCantidadViajesUsuario(id, desde, hasta);
        return new ViajesUsuarioDTO(id, cantidad);
    }

    public List<ViajesUsuarioDTO> getViajesUsuariosDeCuenta(Long idCuenta, Instant desde, Instant hasta) {
        List<Integer> idUsuarios = this.cuentaFeignClient.getUsuariosByCuenta(idCuenta);
        List<ViajesUsuarioDTO> resultado = new ArrayList<>();

        for (Integer idUsuario : idUsuarios) {
            int cantidad = this.viajeFeignClient.getCantidadViajesUsuario(idUsuario, desde, hasta);
            ViajesUsuarioDTO vu = new ViajesUsuarioDTO(idUsuario, cantidad);
            resultado.add(vu);
        }
        return resultado;
    }
}
