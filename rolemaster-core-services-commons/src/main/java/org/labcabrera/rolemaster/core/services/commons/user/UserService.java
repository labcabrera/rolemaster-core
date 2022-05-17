package org.labcabrera.rolemaster.core.services.commons.user;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.user.UserPreferenceModification;
import org.labcabrera.rolemaster.core.model.User;
import org.labcabrera.rolemaster.core.model.exception.NotFoundException;
import org.labcabrera.rolemaster.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Mono<User> findById(String username) {
		return userRepository.findById(username)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("User not found.")));
	}

	public Mono<User> findOrCreate(String username) {
		return userRepository.findById(username).switchIfEmpty(create(username));
	}

	public Mono<User> create(String username) {
		User user = User.builder()
			.id(username)
			.created(LocalDateTime.now())
			.build();
		return userRepository.insert(user);
	}

	public Mono<User> updatePreferences(JwtAuthenticationToken auth, UserPreferenceModification request) {
		return userRepository.findById(auth.getName())
			.switchIfEmpty(Mono.error(() -> new NotFoundException("User not found.")))
			.map(user -> {
				if (request.getDefaultBoardScale() != null) {
					user.setDefaultBoardScale(request.getDefaultBoardScale());
				}
				if (request.getDefaultUnitSystem() != null) {
					user.setDefaultUnitSystem(request.getDefaultUnitSystem());
				}
				if (request.getDefaultUniverseId() != null) {
					user.setDefaultUniverseId(request.getDefaultUniverseId());
				}
				if (request.getDefaultVersion() != null) {
					user.setDefaultVersion(request.getDefaultVersion());
				}
				return user;
			})
			.flatMap(userRepository::save);
	}

}
