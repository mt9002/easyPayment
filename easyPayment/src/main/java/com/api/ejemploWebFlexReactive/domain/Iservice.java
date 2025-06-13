package com.api.ejemploWebFlexReactive.domain;

import com.api.auth.app.service.util.Response;
import reactor.core.publisher.Mono;

public interface Iservice {

    public Mono<Response> suma(double d, double d0);

    public Mono<Response> resta(double d, double d0);

    public Mono<Response> operaciones(double d, double d0);

}
