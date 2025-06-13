package com.api.auth.presentation.util;

import com.api.auth.app.service.util.ResultState;
import static com.api.auth.app.service.util.ResultState.NOT_FOUND;
import static com.api.auth.app.service.util.ResultState.SUCCESS;
import static com.api.auth.app.service.util.ResultState.UNAUTHORIZED;
import static com.api.auth.app.service.util.ResultState.VALIDATION_ERROR;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class MapperStatus {

    public static HttpStatus map(ResultState state) {
        return switch (state) {
            case SUCCESS -> HttpStatus.OK;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case VALIDATION_ERROR -> HttpStatus.BAD_REQUEST;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.NOT_FOUND;
        };
    }
    
    
    // Otra forma
    
    private static final Map<ResultState, HttpStatus> miMap =  Map.of(
            ResultState.SUCCESS, HttpStatus.OK,
            ResultState.UNAUTHORIZED , HttpStatus.UNAUTHORIZED,
            ResultState.VALIDATION_ERROR , HttpStatus.BAD_REQUEST,
            ResultState.NOT_FOUND , HttpStatus.NOT_FOUND
    );
    
    public static HttpStatus map2(ResultState state) {
        return miMap.getOrDefault(state, HttpStatus.BAD_REQUEST);
    }
}
