package org.labcabrera.rolemaster.core.api.controller.impl;

import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.api.controller.UserController;
import org.labcabrera.rolemaster.core.dto.AddFriendRequest;
import org.labcabrera.rolemaster.core.dto.user.UserPreferenceModification;
import org.labcabrera.rolemaster.core.model.User;
import org.labcabrera.rolemaster.core.model.UserFriendRequest;
import org.labcabrera.rolemaster.core.services.commons.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserControllerImpl implements UserController {

	@Autowired
	private UserService userService;

	@Override
	public Mono<User> getUser(JwtAuthenticationToken auth) {
		return userService.findOrCreate(auth.getName());
	}

	@Override
	public Mono<Map<String, Object>> claims(@AuthenticationPrincipal JwtAuthenticationToken auth) {
		return Mono.just(auth.getTokenAttributes());
	}

	@Override
	public Mono<String> token(@AuthenticationPrincipal JwtAuthenticationToken auth) {
		return Mono.just(auth.getToken().getTokenValue());
	}

	@Override
	public Flux<UserFriendRequest> getFriendRequests(@AuthenticationPrincipal JwtAuthenticationToken auth) {
		//TODO
		throw new NotImplementedException();
	}

	@Override
	public Mono<UserFriendRequest> sendFriendRequest(@AuthenticationPrincipal JwtAuthenticationToken auth, AddFriendRequest request) {
		//TODO
		throw new NotImplementedException();
	}

	@Override
	public Mono<Void> removeFriend(JwtAuthenticationToken auth, String friendId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<User> updatePreferences(JwtAuthenticationToken auth, UserPreferenceModification request) {
		return userService.updatePreferences(auth,request);
	}



}
