package org.labcabrera.rolemaster.core.controller.exception;

import org.labcabrera.rolemaster.core.model.ApiError;
import org.labcabrera.rolemaster.core.model.ApiError.Message;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Order(-2)
@Slf4j
public class InternalErrorHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> serverExceptionHandler(Exception ex) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ApiError error = ApiError.builder()
			.message(ex.getMessage())
			.build();

		if (ex instanceof WebExchangeBindException) {
			log.info("Invalid request: {}", ex.getMessage());
			WebExchangeBindException bindEx = (WebExchangeBindException) ex;
			error.setMessage("Invalid request");
			bindEx.getAllErrors().stream().forEach(e -> {
				error.getMessages().add(Message.builder()
					.message(e.getDefaultMessage())
					.build());
			});
			status = HttpStatus.BAD_REQUEST;
		}
		else {

		}

		return new ResponseEntity<>(error, status);
	}

}