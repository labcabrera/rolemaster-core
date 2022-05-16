package org.labcabrera.rolemaster.core.services.commons.security;

import org.labcabrera.rolemaster.core.model.HasAuthorization;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class WriteAuthorizationFilter {

	public <E extends HasAuthorization> E apply(JwtAuthenticationToken auth, E resource) {
		if (resource.getOwner() == null) {
			// No required security
			return resource;
		}
		if (resource.getOwner().equals(auth.getName())) {
			return resource;
		}
		throw new AccessDeniedException("Access denied.");
	}

}
