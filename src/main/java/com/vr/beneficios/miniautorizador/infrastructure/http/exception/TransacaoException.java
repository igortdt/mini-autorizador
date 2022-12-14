package com.vr.beneficios.miniautorizador.infrastructure.http.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoException extends RuntimeException {
    public TransacaoException(String message) {
        super(message);
    }
}
