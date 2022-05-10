package org.labcabrera.rolemaster.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
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
			.pathMatchers("/favicon.ico").permitAll()

			.pathMatchers(HttpMethod.GET, "/races**").permitAll()
			.pathMatchers(HttpMethod.GET, "/professions**").permitAll()
			.pathMatchers(HttpMethod.GET, "/skills**").permitAll()
			.pathMatchers(HttpMethod.GET, "/skill-categories**").permitAll()
			.pathMatchers(HttpMethod.GET, "/enumerations**").permitAll()
			.pathMatchers(HttpMethod.GET, "/items**").permitAll()
			.pathMatchers(HttpMethod.GET, "/languages**").permitAll()
			.pathMatchers(HttpMethod.GET, "/realms**").permitAll()
			.pathMatchers(HttpMethod.GET, "/universes**").permitAll()

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