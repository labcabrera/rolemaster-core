package org.labcabrera.rolemaster.core.model.exception;

@SuppressWarnings("serial")
public class DataConsistenceException extends RuntimeException {

	public DataConsistenceException(String message) {
		super(message);
	}

	public DataConsistenceException(String message, Throwable ex) {
		super(message, ex);
	}

}
