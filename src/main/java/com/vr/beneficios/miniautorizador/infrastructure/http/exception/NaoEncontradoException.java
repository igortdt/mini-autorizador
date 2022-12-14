package com.vr.beneficios.miniautorizador.infrastructure.http.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaoEncontradoException extends RuntimeException {
    public NaoEncontradoException() {
        super();
    }
}
