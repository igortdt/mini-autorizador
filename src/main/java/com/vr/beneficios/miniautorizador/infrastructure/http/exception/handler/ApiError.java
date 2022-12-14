package com.vr.beneficios.miniautorizador.infrastructure.http.exception.handler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

	private HttpStatus status;
	private String message;
	private List<String> errors;
	
	public ApiError(final HttpStatus status, final String message, final String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}
	
	public ApiError(final HttpStatus status, final String message) {
		super();
		this.status = status;
		this.message = message;
		errors = Collections.emptyList();
	}
	
	
}
