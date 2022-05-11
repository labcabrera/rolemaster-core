package org.labcabrera.rolemaster.core.controller;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.User;
import org.labcabrera.rolemaster.core.model.UserFriendRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/user")
@Tag(name = "User", description = "User controller.")
public interface UserController {

	@GetMapping
	Mono<User> getUser(@AuthenticationPrincipal JwtAuthenticationToken auth);

	@GetMapping("/claims")
	Mono<Map<String, Object>> claims(@AuthenticationPrincipal JwtAuthenticationToken auth);

	@GetMapping("/token")
	Mono<String> token(@AuthenticationPrincipal JwtAuthenticationToken auth);

	@GetMapping("/friend-requests")
	public Flux<UserFriendRequest> getFriendRequests(@AuthenticationPrincipal JwtAuthenticationToken auth);

	@PostMapping("/friend-requests")
	public Mono<UserFriendRequest> sendFriendRequest(@AuthenticationPrincipal JwtAuthenticationToken auth);

}
