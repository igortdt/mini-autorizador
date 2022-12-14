package com.vr.beneficios.miniautorizador.infrastructure.http.exception.handler;

import com.fasterxml.jackson.annotation.JsonView;
import com.vr.beneficios.miniautorizador.infrastructure.http.dto.CartaoDTO;
import com.vr.beneficios.miniautorizador.infrastructure.http.exception.CartaoException;
import com.vr.beneficios.miniautorizador.infrastructure.http.exception.CartaoJaCadastradoException;
import com.vr.beneficios.miniautorizador.infrastructure.http.exception.NaoEncontradoException;
import com.vr.beneficios.miniautorizador.infrastructure.http.exception.TransacaoException;
import com.vr.beneficios.miniautorizador.infrastructure.http.view.View;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ErrorValidationHandler {
	
	private static final String LOG_MESSAGE = "ErrorValidationHandler-handle - Exception interceptada -> {}";
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CartaoException.class)
	public ApiError handle(final CartaoException exception) {
		log.info(LOG_MESSAGE, exception.getClass().getName());
		
		return new ApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), exception.getErros());
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NaoEncontradoException.class)
	public void handle(final NaoEncontradoException exception) {
		log.info(LOG_MESSAGE, exception.getClass().getName());
	}

	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(CartaoJaCadastradoException.class)
	@JsonView(value = View.CriarCartaoView.class)
	public CartaoDTO handle(final CartaoJaCadastradoException exception) {
		log.info(LOG_MESSAGE, exception.getClass().getName());
		return exception.getCartaoDTO();
	}

	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(TransacaoException.class)
	public String handle(final TransacaoException exception) {
		log.info(LOG_MESSAGE, exception.getClass().getName());
		return exception.getMessage();
	}

}
