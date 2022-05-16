package org.labcabrera.rolemaster.core.service.user;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.converter.UserFriendRequestConverter;
import org.labcabrera.rolemaster.core.dto.AddFriendRequest;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.User;
import org.labcabrera.rolemaster.core.model.UserFriendRequest;
import org.labcabrera.rolemaster.core.repository.UserFriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;

@Service
@Validated
public class AddFriendRequestService {

	@Autowired
	private UserService userService;

	@Autowired
	private UserFriendRequestConverter converter;

	@Autowired
	private UserFriendRequestRepository userFriendRequestRepository;

	public Mono<UserFriendRequest> apply(JwtAuthenticationToken auth, @Valid AddFriendRequest request) {
		return userService.findById(auth.getName())
			.zipWith(userService.findById(request.getUsername()))
			.map(pair -> check(pair.getT1(), pair.getT2()))
			.map(user -> converter.convert(user, request))
			.flatMap(userFriendRequestRepository::save);
	}

	private User check(User target, User source) {
		if (source.getId().equals(target.getId())) {
			throw new BadRequestException("Invalid user.");
		}
		if (source.getFriends().contains(target.getId())) {
			throw new BadRequestException("Already added.");
		}
		if (source.getBlockedUsers().contains(target.getId())) {
			throw new BadRequestException("Blocked user.");
		}
		return target;
	}

}
