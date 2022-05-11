package org.labcabrera.rolemaster.core;

import static org.mockito.Mockito.lenient;

import org.mockito.Mockito;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class MockAuthentication {

	public static JwtAuthenticationToken mock() {
		JwtAuthenticationToken auth = Mockito.mock(JwtAuthenticationToken.class);
		lenient().when(auth.getName()).thenReturn("test");
		return auth;
	}
}
