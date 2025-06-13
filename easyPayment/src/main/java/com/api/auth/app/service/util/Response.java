package com.api.auth.app.service.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> { // T es un tipo generico, le agrego el tipo requiera.

    private String message;
    private ResultState state;
    private T data;

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(message, ResultState.SUCCESS, data);
    }

    public static <T> Response<T> success(String message) {
        return new Response<>(message, ResultState.SUCCESS, null);
    }

    public static <T> Response<T> failure(String message, ResultState state) {
        return new Response<>(message, state, null);
    }

    public static <T> Response<T> failure(String message, T errorData, ResultState state) {
        return new Response<>(message, state, errorData);
    }

}
