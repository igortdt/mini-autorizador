package com.vr.beneficios.miniautorizador.core.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "cartao")
public class CartaoEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cartao")
    private String numeroCartao;

    private String senha;

    @Column(precision = 10, scale = 2)
    private BigDecimal saldo;

}
