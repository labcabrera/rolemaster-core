package org.labcabrera.rolemaster.core.controller.exception;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
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
		log.debug("Handling exception {}", ex.getClass().getName());

		if (ex instanceof BadRequestException) {
			log.warn("Invalid request", ex);
			ApiError error = ApiError.builder()
				.message(ex.getMessage())
				.build();
			return ResponseEntity.badRequest().body(error);
		}
		if (ex instanceof WebExchangeBindException) {
			log.info("Invalid request: {}", ex.getMessage());
			WebExchangeBindException bindEx = (WebExchangeBindException) ex;
			ApiError error = ApiError.builder()
				.message("Invalid request")
				.build();
			bindEx.getAllErrors().stream().forEach(e -> {
				error.getMessages().add(Message.builder()
					.message(e.getDefaultMessage())
					.build());
			});
			return ResponseEntity.badRequest().body(error);
		}
		else if (ex instanceof NotFoundException) {
			log.warn("Returning not found exception: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}
		else {
			log.error("Internal server error", ex);
		}

		String message = ex.getMessage();
		ApiError error = ApiError.builder()
			.message(StringUtils.isNotBlank(message) ? message : "Internal server error")
			.build();
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}