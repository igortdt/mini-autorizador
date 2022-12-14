package com.vr.beneficios.miniautorizador.core;

import com.vr.beneficios.miniautorizador.core.entity.Cartao;
import com.vr.beneficios.miniautorizador.core.entity.Transacao;
import com.vr.beneficios.miniautorizador.core.mapper.CartaoMapper;
import com.vr.beneficios.miniautorizador.core.repository.CartaoRepository;
import com.vr.beneficios.miniautorizador.core.repository.entity.CartaoEntity;
import com.vr.beneficios.miniautorizador.core.usecase.TransacaoUseCase;
import com.vr.beneficios.miniautorizador.infrastructure.http.exception.TransacaoException;
import com.vr.beneficios.miniautorizador.utils.TestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class TransacaoUseCaseTest {

    @InjectMocks
    TransacaoUseCase transacaoUseCase;

    @Mock
    CartaoRepository cartaoRepository;

    @Spy
    CartaoMapper cartaoMapper = Mappers.getMapper(CartaoMapper.class);

    private static String SALDO_INSUFICIENTE = "SALDO_INSUFICIENTE";
    private static String SENHA_INVALIDA = "SENHA_INVALIDA";
    private static String CARTAO_INEXISTENTE = "CARTAO_INEXISTENTE";

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(transacaoUseCase, "cartaoMapper", cartaoMapper);
    }

    @Test
    void realizarTransacaoSucesso() throws Exception {
        Transacao transacao = TestUtils.getTransacao();
        CartaoEntity cartaoAntesTransacao = TestUtils.getCartaoEntity();
        CartaoEntity cartaoAposTransacao = TestUtils.getCartaoEntity();
        cartaoAposTransacao.setSaldo(cartaoAntesTransacao.getSaldo().subtract(transacao.getSaldo()));
        Mockito.when(cartaoRepository.findByNumeroCartao(Mockito.anyString())).thenReturn(cartaoAntesTransacao);
        Mockito.when(cartaoRepository.save(Mockito.any(CartaoEntity.class))).thenReturn(cartaoAposTransacao);
        Cartao cartao = transacaoUseCase.realizarTransacao(transacao);
        Assert.assertEquals("123456789", cartao.getNumeroCartao());
        Assert.assertEquals(cartaoAposTransacao.getSaldo(), cartaoAntesTransacao.getSaldo().subtract(transacao.getSaldo()));
    }

    @Test
    void realizarTransacaoCartaoInexistenteException() throws Exception {
        Mockito.when(cartaoRepository.findByNumeroCartao(Mockito.anyString())).thenReturn(null);
        Exception exception = Assert.assertThrows(TransacaoException.class,
                () -> transacaoUseCase.realizarTransacao(TestUtils.getTransacao()));
        Assert.assertEquals(CARTAO_INEXISTENTE, exception.getMessage());
    }

    @Test
    void realizarTransacaoSaldoInsuficienteException() throws Exception {
        Transacao transacao = TestUtils.getTransacao();
        transacao.setSaldo(new BigDecimal(850));
        Mockito.when(cartaoRepository.findByNumeroCartao(Mockito.anyString())).thenReturn(TestUtils.getCartaoEntity());
        Exception exception = Assert.assertThrows(TransacaoException.class,
                () -> transacaoUseCase.realizarTransacao(transacao));
        Assert.assertEquals(SALDO_INSUFICIENTE, exception.getMessage());
    }

    @Test
    void realizarTransacaoSenhaInvalidaException() throws Exception {
        Transacao transacao = TestUtils.getTransacao();
        transacao.setSenha("senha_errada");
        Mockito.when(cartaoRepository.findByNumeroCartao(Mockito.anyString())).thenReturn(TestUtils.getCartaoEntity());
        Exception exception = Assert.assertThrows(TransacaoException.class,
                () -> transacaoUseCase.realizarTransacao(transacao));
        Assert.assertEquals(SENHA_INVALIDA, exception.getMessage());
    }
}
