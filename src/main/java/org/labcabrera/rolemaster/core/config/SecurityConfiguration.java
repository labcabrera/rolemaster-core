package org.labcabrera.rolemaster.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
		Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter) {

		http.authorizeExchange()
			.pathMatchers("/").permitAll()
			.pathMatchers("/swagger-ui.html").permitAll()
			.pathMatchers("/webjars/**").permitAll()
			.pathMatchers("/v3/api-docs/**").permitAll()
			.pathMatchers("/actuator/**").permitAll()

			//TODO
			.pathMatchers("/skills").hasAnyAuthority("role_user", "role_admin", "role_colaborator")

			.anyExchange().authenticated()
			.and()
			.oauth2ResourceServer()
			.jwt()
			.jwtAuthenticationConverter(jwtAuthenticationConverter);
		return http.build();
	}
}