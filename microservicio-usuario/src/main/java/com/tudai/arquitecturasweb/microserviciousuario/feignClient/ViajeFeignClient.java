package com.tudai.arquitecturasweb.microserviciousuario.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;

@FeignClient(name="microservicio-viaje", url="http://microservicio-viaje:8005/viajes")
public interface ViajeFeignClient {
    @GetMapping("/cantidad-por-usuario/{idUsuario}")
    int getCantidadViajesUsuario(@PathVariable("idUsuario") int idUsuario,
                                 @RequestParam("desde") Instant desde,
                                 @RequestParam("hasta") Instant hasta);
}
