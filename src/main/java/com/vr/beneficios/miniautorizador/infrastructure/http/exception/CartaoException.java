package com.vr.beneficios.miniautorizador.infrastructure.http.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartaoException extends RuntimeException {
    private List<String> erros;

    public CartaoException(String message, final String error) {
        super(message);
        erros = Arrays.asList(error);
    }
}
