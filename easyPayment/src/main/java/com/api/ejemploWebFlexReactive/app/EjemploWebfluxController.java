package com.api.ejemploWebFlexReactive.app;

import com.api.ejemploWebFlexReactive.domain.Iservice;
import com.api.auth.app.service.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/calculadora")
public class EjemploWebfluxController {

    private final Iservice serviceCalculadora;

    @Autowired
    public EjemploWebfluxController(Iservice serviceCalculadora) {
        this.serviceCalculadora = serviceCalculadora;
    }

    @GetMapping("/operaciones")
    public Mono<Response> calculator(@RequestParam double a, @RequestParam double b) {
       
        Mono<Response> resp= serviceCalculadora.operaciones(a, b);

        return resp;
    }
}
