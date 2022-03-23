package org.labcabrera.rolemaster.core.exception;

import lombok.Getter;

@Getter
@SuppressWarnings("serial")
public class SessionNotFoundException extends NotFoundException {

	private final String sessionId;

	public SessionNotFoundException(String sessionId) {
		super(String.format("Session %s not found", sessionId));
		this.sessionId = sessionId;
	}

}
