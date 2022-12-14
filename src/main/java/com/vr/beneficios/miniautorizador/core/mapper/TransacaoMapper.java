package com.vr.beneficios.miniautorizador.core.mapper;

import com.vr.beneficios.miniautorizador.core.entity.Cartao;
import com.vr.beneficios.miniautorizador.core.entity.Transacao;
import com.vr.beneficios.miniautorizador.core.repository.entity.CartaoEntity;
import com.vr.beneficios.miniautorizador.infrastructure.http.dto.CartaoDTO;
import com.vr.beneficios.miniautorizador.infrastructure.http.dto.TransacaoDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TransacaoMapper {
    Transacao toTransacao(TransacaoDTO transacaoDTO);
}
