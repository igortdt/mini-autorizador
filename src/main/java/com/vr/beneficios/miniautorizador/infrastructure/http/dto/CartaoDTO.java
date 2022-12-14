package com.vr.beneficios.miniautorizador.infrastructure.http.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.vr.beneficios.miniautorizador.infrastructure.http.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartaoDTO {

    @NotBlank
    @JsonView(value = View.CriarCartaoView.class)
    private String numeroCartao;

    @NotBlank
    @JsonView(value = View.CriarCartaoView.class)
    private String senha;

    private BigDecimal saldo;
}
