package com.vr.beneficios.miniautorizador.core.usecase;

import com.vr.beneficios.miniautorizador.core.entity.Cartao;
import com.vr.beneficios.miniautorizador.core.entity.Transacao;
import com.vr.beneficios.miniautorizador.core.mapper.CartaoMapper;
import com.vr.beneficios.miniautorizador.core.repository.CartaoRepository;
import com.vr.beneficios.miniautorizador.core.repository.entity.CartaoEntity;
import com.vr.beneficios.miniautorizador.infrastructure.http.exception.TransacaoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class TransacaoUseCase {

    private static String SALDO_INSUFICIENTE = "SALDO_INSUFICIENTE";
    private static String SENHA_INVALIDA = "SENHA_INVALIDA";
    private static String CARTAO_INEXISTENTE = "CARTAO_INEXISTENTE";

    private final CartaoRepository cartaoRepository;

    private final CartaoMapper cartaoMapper;

    public Cartao realizarTransacao(Transacao transacao) {
        Cartao cartao = validarTransacao(transacao);
        try {
            BigDecimal novoSaldo = cartao.getSaldo().subtract(transacao.getSaldo().setScale(2, RoundingMode.HALF_EVEN));
            cartao.setSaldo(novoSaldo);
            CartaoEntity cartaoAtualizado = cartaoRepository.save(cartaoMapper.toCartaoEntity(cartao));
            log.info(">>> Transacao no valor de {} no cartao {} realizada com sucesso.", transacao.getSaldo(), transacao.getNumeroCartao());
            return cartaoMapper.toCartao(cartaoAtualizado);
        } catch (Exception e) {
            log.error(">>> Erro ao realizar transacao no cartao: " + e.getMessage());
            throw new TransacaoException("Erro ao realizar trasação no cartao: " + transacao.getNumeroCartao());
        }
    }

    private Cartao validarTransacao(Transacao transacao){
        Cartao cartao = validarCartaoInexistente(transacao.getNumeroCartao());
        validarSaldo(cartao, transacao);
        validarSenha(cartao, transacao);
        return cartao;
    }

    private Cartao validarCartaoInexistente(String numeroCartao) {
        log.info(">>> Validando existencia do cartao");
        CartaoEntity cartaoEntity = cartaoRepository.findByNumeroCartao(numeroCartao);
        return cartaoMapper.toCartao(Optional.ofNullable(cartaoEntity)
                .orElseThrow(() -> {
                    log.error("Cartao {} indexistente.", numeroCartao);
            throw new TransacaoException(CARTAO_INEXISTENTE);
        }));
    }

    private void validarSaldo(Cartao cartao, Transacao transacao){
        log.info(">>> Validando saldo do cartao");
        boolean transacaoPermitida = cartao.getSaldo().compareTo(transacao.getSaldo().setScale(2, RoundingMode.HALF_EVEN)) >= 0;
        Optional.ofNullable(!transacaoPermitida ? null : transacaoPermitida)
                .orElseThrow(() -> {
                    log.error("Saldo do cartao {} indexistente.", transacao.getNumeroCartao());
                    throw new TransacaoException(SALDO_INSUFICIENTE);
                });
    }

    private void validarSenha(Cartao cartao, Transacao transacao){
        log.info(">>> Validando senha do cartao");
        boolean transacaoPermitida = cartao.getSenha().equalsIgnoreCase(transacao.getSenha());
        Optional.ofNullable(!transacaoPermitida ? null : transacaoPermitida)
                .orElseThrow(() -> {
                    log.error("Senha do cartao {} invalida.", transacao.getNumeroCartao());
                    throw new TransacaoException(SENHA_INVALIDA);
                });
    }
}
