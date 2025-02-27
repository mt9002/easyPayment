package com.api.domain.interfaces.incoming;

import com.api.domain.services.util.Response;
import reactor.core.publisher.Mono;

public interface Iservice {

    public Mono<Response> suma(double d, double d0);

    public Mono<Response> resta(double d, double d0);

    public Mono<Response> operaciones(double d, double d0);

}
