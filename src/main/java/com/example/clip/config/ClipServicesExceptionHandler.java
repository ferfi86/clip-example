package com.example.clip.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.example.clip.model.exceptions.ClipServiceErrorMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ClipServicesExceptionHandler {

	@ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentTypeMismatchException ex) {
		log.error("Argument mismatch");
		return new ResponseEntity<>(
				ClipServiceErrorMessage.builder().httpStatus(HttpStatus.BAD_REQUEST.value())
						.developerMessage(ex.getMessage()).message("Argument mismatch").build(),
				HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = { ResponseStatusException.class })
	protected ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(ClipServiceErrorMessage.builder().httpStatus(ex.getRawStatusCode())
				.developerMessage(ex.getMessage()).message(ex.getMessage()).build(),
				HttpStatus.valueOf(ex.getRawStatusCode()));

	}
}
