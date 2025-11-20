package com.tudai.arquitecturasweb.microservicioadministracion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioAdministracionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioAdministracionApplication.class, args);
    }

}
