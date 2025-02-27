package com.api.app.controller;

import com.api.domain.interfaces.incoming.Iservice;
import com.api.domain.services.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Mono<Response> sumas(){  
        return serviceCalculadora.operaciones(4 , 3);
    }
         
}

