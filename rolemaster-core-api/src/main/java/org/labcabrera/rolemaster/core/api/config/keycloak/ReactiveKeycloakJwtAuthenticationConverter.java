package org.labcabrera.rolemaster.core.api.config.keycloak;

import java.util.Collection;

import org.labcabrera.rolemaster.core.model.exception.JwtDecodeException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.util.Assert;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class ReactiveKeycloakJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

	private static final String USERNAME_CLAIM = "preferred_username";
	private final Converter<Jwt, Flux<GrantedAuthority>> jwtGrantedAuthoritiesConverter;

	public ReactiveKeycloakJwtAuthenticationConverter(Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
		Assert.notNull(jwtGrantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null");
		this.jwtGrantedAuthoritiesConverter = new ReactiveJwtGrantedAuthoritiesConverterAdapter(
			jwtGrantedAuthoritiesConverter);
	}

	@Override
	public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
		Flux<GrantedAuthority> flux = jwtGrantedAuthoritiesConverter.convert(jwt);
		if (flux != null) {
			return flux.collectList().map(authorities -> new JwtAuthenticationToken(jwt, authorities, extractUsername(jwt)));
		}
		else {
			throw new JwtDecodeException("JWT convertion error.");
		}
	}

	protected String extractUsername(Jwt jwt) {
		return jwt.hasClaim(USERNAME_CLAIM) ? jwt.getClaimAsString(USERNAME_CLAIM) : jwt.getSubject();
	}

}