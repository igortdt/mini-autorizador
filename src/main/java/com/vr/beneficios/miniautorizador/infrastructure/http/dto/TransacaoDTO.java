package com.vr.beneficios.miniautorizador.infrastructure.http.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransacaoDTO {

    @NotBlank
    private String numeroCartao;

    @NotBlank
    private String senha;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal saldo;
}
