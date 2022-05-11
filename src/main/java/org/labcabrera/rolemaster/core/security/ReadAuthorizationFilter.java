package org.labcabrera.rolemaster.core.security;

import org.labcabrera.rolemaster.core.model.HasAuthorization;
import org.labcabrera.rolemaster.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ReadAuthorizationFilter {

	@Autowired
	private UserRepository userRepository;

	public <E extends HasAuthorization> Mono<E> apply(JwtAuthenticationToken auth, E resource) {
		if (resource.getOwner() == null || resource.getOwner().equals(auth.getName())) {
			return Mono.just(resource);
		}
		return userRepository.findById(auth.getName())
			.map(e -> e.getFriends())
			.map(friends -> {
				if (!friends.contains(resource.getOwner())) {
					throw new AccessDeniedException("Access denied.");
				}
				return resource;
			});
	}

}
