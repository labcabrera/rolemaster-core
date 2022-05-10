package org.labcabrera.rolemaster.core.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@Tag(name = "Characters", description = "Operations on characters such as search, creation, configuration or leveling up.")
public class UserController {

	@GetMapping
	public Mono<Map<String, Object>> claims(@AuthenticationPrincipal JwtAuthenticationToken auth) {
		return Mono.just(auth.getTokenAttributes());
	}

	@GetMapping("/token")
	public Mono<String> token(@AuthenticationPrincipal JwtAuthenticationToken auth) {
		return Mono.just(auth.getToken().getTokenValue());
	}

}
