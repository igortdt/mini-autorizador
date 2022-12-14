package com.vr.beneficios.miniautorizador.core.usecase;

import com.vr.beneficios.miniautorizador.core.entity.Cartao;
import com.vr.beneficios.miniautorizador.core.mapper.CartaoMapper;
import com.vr.beneficios.miniautorizador.core.repository.CartaoRepository;
import com.vr.beneficios.miniautorizador.core.repository.entity.CartaoEntity;
import com.vr.beneficios.miniautorizador.infrastructure.http.exception.CartaoException;
import com.vr.beneficios.miniautorizador.infrastructure.http.exception.CartaoJaCadastradoException;
import com.vr.beneficios.miniautorizador.infrastructure.http.exception.NaoEncontradoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class CartaoUseCase {

    private static String saldoNovoCartao = "500";

    private final CartaoRepository cartaoRepository;
    private final CartaoMapper cartaoMapper;

    public Cartao salvar(Cartao cartao) {
        validarCartaoJaExistente(cartao.getNumeroCartao());
        try {
            cartao.setSaldo(new BigDecimal(saldoNovoCartao));
            CartaoEntity cartaoSalvo = cartaoRepository.save(cartaoMapper.toCartaoEntity(cartao));
            return cartaoMapper.toCartao(cartaoSalvo);
        } catch (Exception e) {
            log.error(">>> Erro ao salvar cartão: " + e.getMessage());
            throw new CartaoException("Erro ao salvar cartão", "Erro ao salvar o cartão de númeroo: " + cartao.getNumeroCartao());
        }
    }

    private void validarCartaoJaExistente(String numeroCartao) {
        log.error(">>> Relizando validacao de conta ja cadastrada.");
        CartaoEntity cartaoEntity = cartaoRepository.findByNumeroCartao(numeroCartao);
        Optional.ofNullable(Objects.nonNull(cartaoEntity) ? null : CartaoEntity.builder().build())
                .orElseThrow(() -> {
                    log.error(">>> Cartao {} ja cadastrado.", numeroCartao);
            Cartao cartao = cartaoMapper.toCartao(cartaoEntity);
            throw new CartaoJaCadastradoException(cartaoMapper.toCartaoDTO(cartao));
        });
    }

    public Cartao getCartaoByNumero(String numeroCartao) {
        CartaoEntity cartaoEntity = cartaoRepository.findByNumeroCartao(numeroCartao);
        return cartaoMapper.toCartao(Optional.ofNullable(cartaoEntity)
                .orElseThrow(() -> {
                    throw new NaoEncontradoException();
                }));
    }

}
