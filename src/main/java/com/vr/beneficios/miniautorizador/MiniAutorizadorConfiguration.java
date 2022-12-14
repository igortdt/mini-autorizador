package com.vr.beneficios.miniautorizador;

import com.vr.beneficios.miniautorizador.core.mapper.CartaoMapper;
import com.vr.beneficios.miniautorizador.core.repository.CartaoRepository;
import com.vr.beneficios.miniautorizador.core.usecase.CartaoUseCase;
import com.vr.beneficios.miniautorizador.core.usecase.TransacaoUseCase;
import lombok.Generated;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Generated
@Configuration
public class MiniAutorizadorConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	CartaoUseCase cartaoUseCase(CartaoRepository cartaoRepository, CartaoMapper cartaoMapper){
		return new CartaoUseCase(cartaoRepository, cartaoMapper);
	}

	@Bean
	TransacaoUseCase transacaoUseCase(CartaoRepository cartaoRepository, CartaoMapper cartaoMapper){
		return new TransacaoUseCase(cartaoRepository, cartaoMapper);
	}
}
