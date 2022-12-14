package com.vr.beneficios.miniautorizador.utils;

import com.vr.beneficios.miniautorizador.core.entity.Cartao;
import com.vr.beneficios.miniautorizador.core.entity.Transacao;
import com.vr.beneficios.miniautorizador.core.repository.entity.CartaoEntity;
import com.vr.beneficios.miniautorizador.infrastructure.http.dto.CartaoDTO;
import com.vr.beneficios.miniautorizador.infrastructure.http.dto.TransacaoDTO;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class TestUtils {

    public static CartaoDTO getCartaoDTO(){
        return CartaoDTO.builder()
                .numeroCartao("123456789")
                .senha("1234")
                .saldo(new BigDecimal(500))
                .build();
    }

    public static Cartao getCartao(){
        return Cartao.builder()
                .numeroCartao("123456789")
                .senha("1234")
                .saldo(new BigDecimal(500))
                .build();
    }

    public static CartaoEntity getCartaoEntity(){
        return CartaoEntity.builder()
                .id(10L)
                .numeroCartao("123456789")
                .senha("1234")
                .saldo(new BigDecimal(500))
                .build();
    }

    public static TransacaoDTO getTransacaoDTO(){
        return TransacaoDTO.builder()
                .numeroCartao("123456789")
                .senha("1234")
                .saldo(new BigDecimal(300))
                .build();
    }

    public static Transacao getTransacao(){
        return Transacao.builder()
                .numeroCartao("123456789")
                .senha("1234")
                .saldo(new BigDecimal(300))
                .build();
    }
}
