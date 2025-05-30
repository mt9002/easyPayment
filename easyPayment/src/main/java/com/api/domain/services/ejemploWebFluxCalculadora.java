package com.api.domain.services;

import com.api.domain.interfaces.incoming.Iservice;
import com.api.domain.services.util.Response;
import java.time.Duration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ejemploWebFluxCalculadora implements Iservice {

    @Override
    public Mono<Response> suma(double d, double d0) {
        double data = d + d0;
        System.nanoTime();
        return Mono.delay(Duration.ofSeconds(2)) // Simula un retraso no bloqueante de 1 segundo
                .then(Mono.fromCallable(() -> {
                    System.nanoTime();
                    return new Response("OK bien", 200, true, data);
                }));
    }

    @Override
    public Mono<Response> resta(double d, double d0) {
        double data = d - d0; // Corregido: ahora resta en lugar de sumar
        System.nanoTime();
        return Mono.delay(Duration.ofSeconds(2)) // Simula un retraso no bloqueante de 1 segundo
                .then(Mono.fromCallable(() -> {
                    System.nanoTime();
                    return new Response("OK bien", 200, true, data);
                }));
    }

    @Override
    public Mono<Response> operaciones(double d, double d0) {
        System.nanoTime();
        Mono<Response> sumaResult1 = suma(d, d0);
        Mono<Response> restaResult2 = resta(d, d0);
        Mono<Response> restaResult3 = resta(d, d0);
        Mono<Response> restaResult4 = resta(d, d0);
        Mono<Response> restaResult5 = resta(d, d0);
        Mono<Response> restaResult6 = resta(d, d0);
        Mono<Response> restaResult7 = resta(d, d0);
        System.nanoTime();
       
        return Mono.zip(sumaResult1, restaResult2, restaResult3, restaResult4, restaResult5, restaResult6, restaResult7)
                .map(tuple -> {
                    Response sumaResponse = tuple.getT1();
                    Response restaResponse = tuple.getT2();
                    return new Response("Operaciones completadas", 200, true, sumaResponse.getData() + ", " + restaResponse.getData());
                });
    }
}
