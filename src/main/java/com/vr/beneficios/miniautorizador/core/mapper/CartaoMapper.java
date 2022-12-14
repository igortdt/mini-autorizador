package com.vr.beneficios.miniautorizador.core.mapper;

import com.vr.beneficios.miniautorizador.core.entity.Cartao;
import com.vr.beneficios.miniautorizador.core.repository.entity.CartaoEntity;
import com.vr.beneficios.miniautorizador.infrastructure.http.dto.CartaoDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CartaoMapper {
    Cartao toCartao(CartaoDTO cartaoDTO);
    Cartao toCartao(CartaoEntity cartaoEntity);
    CartaoDTO toCartaoDTO(Cartao cartao);
    CartaoEntity toCartaoEntity(Cartao cartao);
}
