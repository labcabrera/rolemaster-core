package org.labcabrera.rolemaster.core.model.exception;

@SuppressWarnings("serial")
public class JwtDecodeException extends RuntimeException {

	public JwtDecodeException(String message) {
		super(message);
	}

}
