package com.vr.beneficios.miniautorizador.infrastructure.http;


import com.fasterxml.jackson.annotation.JsonView;
import com.vr.beneficios.miniautorizador.core.entity.Cartao;
import com.vr.beneficios.miniautorizador.core.mapper.CartaoMapper;
import com.vr.beneficios.miniautorizador.core.usecase.CartaoUseCase;
import com.vr.beneficios.miniautorizador.infrastructure.http.dto.CartaoDTO;
import com.vr.beneficios.miniautorizador.infrastructure.http.view.View;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartaoController {

    private final CartaoUseCase cartaoUseCase;
    private final CartaoMapper cartaoMapper;

    @PostMapping
    @JsonView(value = View.CriarCartaoView.class)
    public ResponseEntity<Object> criarCartao(@RequestBody @Valid CartaoDTO cartaoDTO){
        log.info(">>> Iniciando metodo criarCartao para o numero {}", cartaoDTO.getNumeroCartao());
        Cartao cartao = cartaoMapper.toCartao(cartaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoMapper.toCartaoDTO(cartaoUseCase.salvar(cartao)));
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<Object> buscarSaldo(@PathVariable("numeroCartao") String numeroCartao){
        log.info(">>> Iniciando metodo buscarSaldo para o numero {}", numeroCartao);
        CartaoDTO cartaoDTO = cartaoMapper.toCartaoDTO(cartaoUseCase.getCartaoByNumero(numeroCartao));
        return ResponseEntity.ok(cartaoDTO.getSaldo());
    }

}
