package org.labcabrera.rolemaster.core.controller.exception;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.ApiError;
import org.labcabrera.rolemaster.core.model.ApiError.ApiErrorMessage;
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

	private static final String INVALID_REQUEST = "Invalid request";
	private static final String INVALID_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());

	private static final String INTERNAL_SERVER_ERROR = "Internal server error";
	private static final String INTERNAL_SERVER_CODE = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> serverExceptionHandler(Exception ex) {
		log.debug("Handling exception {}", ex.getClass().getName());

		if (ex instanceof BadRequestException) {
			log.warn(INVALID_REQUEST, ex);
			ApiError error = ApiError.builder()
				.message(ex.getMessage())
				.code(INVALID_REQUEST_CODE)
				.build();
			return ResponseEntity.badRequest().body(error);
		}
		else if (ex instanceof ConstraintViolationException) {
			log.warn(INVALID_REQUEST, ex.getMessage());
			ApiError error = ApiError.builder()
				.message(ex.getMessage())
				.code(INVALID_REQUEST_CODE)
				.build();
			return ResponseEntity.badRequest().body(error);
		}
		else if (ex instanceof WebExchangeBindException bindException) {
			log.info("Invalid request: {}", ex.getMessage());
			ApiError error = ApiError.builder()
				.code(INVALID_REQUEST_CODE)
				.message(INVALID_REQUEST)
				.build();
			bindException.getAllErrors().stream().forEach(e -> error.getMessages().add(ApiErrorMessage.builder()
				.message(e.getDefaultMessage())
				.build()));
			return ResponseEntity.badRequest().body(error);
		}
		else if (ex instanceof NotFoundException) {
			log.warn("Returning not found exception: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}
		else {
			log.error(INTERNAL_SERVER_ERROR, ex);
		}

		String message = ex.getMessage();
		ApiError error = ApiError.builder()
			.code(INTERNAL_SERVER_CODE)
			.message(StringUtils.isNotBlank(message) ? message : INTERNAL_SERVER_ERROR)
			.build();
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}