package com.vr.beneficios.miniautorizador.infrastructure.http;


import com.vr.beneficios.miniautorizador.core.entity.Transacao;
import com.vr.beneficios.miniautorizador.core.mapper.TransacaoMapper;
import com.vr.beneficios.miniautorizador.core.usecase.TransacaoUseCase;
import com.vr.beneficios.miniautorizador.infrastructure.http.dto.TransacaoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoUseCase transacaoUseCase;
    private final TransacaoMapper transacaoMapper;

    @PostMapping
    public ResponseEntity<Object> transacao(@RequestBody @Valid TransacaoDTO transacaoDTO){
        log.info(">>> Iniciando metodo transacao para o cartao {}", transacaoDTO.getNumeroCartao());
        transacaoUseCase.realizarTransacao(transacaoMapper.toTransacao(transacaoDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }
}
