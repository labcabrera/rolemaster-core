package org.labcabrera.rolemaster.core.security;

import org.labcabrera.rolemaster.core.model.HasAuthorization;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationConsumer {

	public <E extends HasAuthorization> E accept(JwtAuthenticationToken auth, E resource) {
		if (resource.getOwner() != null) {
			throw new RuntimeException("Owner must be null.");
		}
		resource.setOwner(auth.getName());
		return resource;
	}

}
