package com.vr.beneficios.miniautorizador.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    private String numeroCartao;
    private String senha;
    private BigDecimal saldo;
}
