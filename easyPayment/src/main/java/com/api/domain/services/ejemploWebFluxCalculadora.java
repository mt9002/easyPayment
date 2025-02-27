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
        long startTime = System.nanoTime();
        return Mono.delay(Duration.ofSeconds(2)) // Simula un retraso no bloqueante de 1 segundo
                .then(Mono.fromCallable(() -> {
                    long endTime = System.nanoTime();
                    System.out.println("suma realizada. Tiempo: " + (endTime - startTime) / 1_000_000 + " ms");
                    return new Response("OK bien", 200, true, data);
                }));
    }

    @Override
    public Mono<Response> resta(double d, double d0) {
        double data = d - d0; // Corregido: ahora resta en lugar de sumar
        long startTime = System.nanoTime();
        return Mono.delay(Duration.ofSeconds(2)) // Simula un retraso no bloqueante de 1 segundo
                .then(Mono.fromCallable(() -> {
                    long endTime = System.nanoTime();
                    System.out.println("Resta realizada. Tiempo: " + (endTime - startTime) / 1_000_000 + " ms");
                    return new Response("OK bien", 200, true, data);
                }));
    }

    @Override
    public Mono<Response> operaciones(double d, double d0) {
        long startTime = System.nanoTime();
        Mono<Response> sumaResult1 = suma(d, d0);
        Mono<Response> restaResult2 = resta(d, d0);
        Mono<Response> restaResult3 = resta(d, d0);
        Mono<Response> restaResult4 = resta(d, d0);
        Mono<Response> restaResult5 = resta(d, d0);
        Mono<Response> restaResult6 = resta(d, d0);
        Mono<Response> restaResult7 = resta(d, d0);
        long endTime = System.nanoTime();
        System.out.println("resulta total del tiempo: " + (endTime - startTime) / 1_000_000 + " ms");
       
        return Mono.zip(sumaResult1, restaResult2, restaResult3, restaResult4, restaResult5, restaResult6, restaResult7)
                .map(tuple -> {
                    Response sumaResponse = tuple.getT1();
                    Response restaResponse = tuple.getT2();

                    // Aquí puedes combinar los resultados como desees
                    // Por ejemplo, podrías devolver un nuevo Response con ambos resultados
                    return new Response("Operaciones completadas", 200, true, sumaResponse.getData() + ", " + restaResponse.getData());
                });
    }
}
