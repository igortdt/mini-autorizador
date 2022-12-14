package com.vr.beneficios.miniautorizador.infrastructure.http.exception;

import com.vr.beneficios.miniautorizador.infrastructure.http.dto.CartaoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartaoJaCadastradoException extends RuntimeException {
    CartaoDTO cartaoDTO;
    public CartaoJaCadastradoException(CartaoDTO cartaoDTO) {
        super();
        this.cartaoDTO = cartaoDTO;
    }
}
