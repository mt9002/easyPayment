package com.api.util;

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
    private int status;
    private boolean success;
    private T data;
}