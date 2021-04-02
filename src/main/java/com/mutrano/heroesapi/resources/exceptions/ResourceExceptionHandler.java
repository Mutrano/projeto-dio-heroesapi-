package com.mutrano.heroesapi.resources.exceptions;
import java.net.URI;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.mutrano.heroesapi.services.exceptions.ResourceNotFoundException;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public Mono<ResponseEntity<StandardError>> resourceNotFound(ResourceNotFoundException expn ){
		String error = "Resource not found";
		String message = "Try a different one";
		HttpStatus status = HttpStatus.NOT_FOUND;
		return Mono.just(ResponseEntity.status(status).body(
			    new StandardError(Instant.now(), status.value(), error, expn.getRequest(), message)));
	}
	
	@ExceptionHandler(WebExchangeBindException.class)
	public Mono<ResponseEntity<ValidationError>> validationError(WebExchangeBindException expn){
		String error = "Validation error";
		String message= "pain";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ValidationError err = new ValidationError(Instant.now(), status.value(), error,URI.create("/heroes").toString()  , message);

			expn.getBindingResult().getFieldErrors().stream()
				.forEach(fieldError-> err.addError(fieldError.getField(),fieldError.getDefaultMessage()));
		return Mono.just(ResponseEntity.status(status).body(
				err
				));

	}
}
