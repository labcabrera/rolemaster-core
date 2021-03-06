package org.labcabrera.rolemaster.core.api.exception;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.rolemaster.core.model.ApiError;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.exception.NotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

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

		if (ex instanceof AccessDeniedException) {
			log.warn("Access denied", ex);
			ApiError error = ApiError.builder()
				.message(ex.getMessage())
				.code("403")
				.build();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
		}
		if (ex instanceof BadRequestException) {
			log.warn(INVALID_REQUEST, ex);
			ApiError error = ApiError.builder()
				.message(ex.getMessage())
				.code(INVALID_REQUEST_CODE)
				.build();
			return ResponseEntity.badRequest().body(error);
		}
		else if (ex instanceof ConstraintViolationException) {
			log.warn(INVALID_REQUEST, ex.getMessage(), ex);
			ApiError error = ApiError.builder()
				.message(ex.getMessage())
				.code(INVALID_REQUEST_CODE)
				.build();
			return ResponseEntity.badRequest().body(error);
		}
		else if (ex instanceof BindingResult bindException) {
			String message = getBindExceptionMessage(bindException);
			ApiError error = ApiError.builder().code(INVALID_REQUEST_CODE).message(message).build();
			log.info("Invalid request: {}", message, bindException);
			return ResponseEntity.badRequest().body(error);
		}
		else if (ex instanceof ServerWebInputException inputException) {
			String message = getServerWebInputExceptionMessage(inputException);
			log.info("Invalid request: {}", message, inputException);
			ApiError error = ApiError.builder().code(INVALID_REQUEST_CODE).message(message).build();
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

	private String getBindExceptionMessage(BindingResult ex) {
		StringBuilder sb = new StringBuilder("Invalid request.");
		List<FieldError> errors = ex.getFieldErrors();
		for (FieldError error : errors) {
			sb.append(" ")
				.append(error.getField())
				.append(" ")
				.append(error.getDefaultMessage())
				.append(".");
		}
		return sb.toString();
	}

	private String getServerWebInputExceptionMessage(ServerWebInputException ex) {
		StringBuilder sb = new StringBuilder();
		sb.append("Invalid request.");
		if (ex.getReason() != null) {
			sb.append(" ").append(ex.getReason());
		}
		sb.append(".");
		return sb.toString();
	}

}