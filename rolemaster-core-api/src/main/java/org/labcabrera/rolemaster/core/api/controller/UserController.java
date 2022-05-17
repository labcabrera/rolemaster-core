package org.labcabrera.rolemaster.core.api.controller;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.AddFriendRequest;
import org.labcabrera.rolemaster.core.dto.user.UserPreferenceModification;
import org.labcabrera.rolemaster.core.model.User;
import org.labcabrera.rolemaster.core.model.UserFriendRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	Flux<UserFriendRequest> getFriendRequests(@AuthenticationPrincipal JwtAuthenticationToken auth);

	@PostMapping("/friend-requests")
	Mono<UserFriendRequest> sendFriendRequest(@AuthenticationPrincipal JwtAuthenticationToken auth, AddFriendRequest request);

	@DeleteMapping("/friends/{friendId}")
	Mono<Void> removeFriend(@AuthenticationPrincipal JwtAuthenticationToken auth, @PathVariable String friendId);

	@PatchMapping("/preferences")
	Mono<User> updatePreferences(@AuthenticationPrincipal JwtAuthenticationToken auth, @RequestBody UserPreferenceModification request);

}
