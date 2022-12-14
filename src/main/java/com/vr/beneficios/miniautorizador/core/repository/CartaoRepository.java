package com.vr.beneficios.miniautorizador.core.repository;

import com.vr.beneficios.miniautorizador.core.repository.entity.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<CartaoEntity, Long> {

    CartaoEntity findByNumeroCartao(@Param("numeroCartao") String numeroCartao);

}
