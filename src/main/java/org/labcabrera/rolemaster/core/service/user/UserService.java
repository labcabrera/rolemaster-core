package org.labcabrera.rolemaster.core.service.user;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.User;
import org.labcabrera.rolemaster.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

}
