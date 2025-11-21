package com.tudai.arquitecturasweb.microserviciousuario.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient(name="microservicio-viaje", url="http://localhost:8003/viajes")
public interface ViajeFeignClient {
    @GetMapping("/cantidad-por-usuario/{idUsuario}")
    int getCantidadViajesUsuario(@PathVariable("idUsuario") int idUsuario,
                                 @RequestParam("desde") LocalDateTime desde,
                                 @RequestParam("hasta") LocalDateTime hasta);
}
