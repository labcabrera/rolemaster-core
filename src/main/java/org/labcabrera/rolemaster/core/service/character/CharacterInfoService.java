package org.labcabrera.rolemaster.core.service.character;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.User;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.security.AuthorizationConsumer;
import org.labcabrera.rolemaster.core.security.ReadAuthorizationFilter;
import org.labcabrera.rolemaster.core.security.Role;
import org.labcabrera.rolemaster.core.security.WriteAuthorizationFilter;
import org.labcabrera.rolemaster.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CharacterInfoService {

	@Autowired
	private UserService userService;

	@Autowired
	private CharacterInfoRepository characterRepository;

	@Autowired
	private AuthorizationConsumer authorizationConsumer;

	@Autowired
	private ReadAuthorizationFilter readAuthorizationFilter;

	@Autowired
	private WriteAuthorizationFilter writeAuthorizationFilter;

	public Mono<CharacterInfo> insert(JwtAuthenticationToken auth, CharacterInfo character) {
		authorizationConsumer.accept(auth, character);
		return characterRepository.save(character);
	}

	public Mono<CharacterInfo> update(JwtAuthenticationToken auth, CharacterInfo character) {
		return characterRepository.findById(character.getId())
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Character not found.")))
			.map(e -> writeAuthorizationFilter.apply(auth, e))
			.then(characterRepository.save(character));
	}

	public Mono<CharacterInfo> findById(JwtAuthenticationToken auth, String id) {
		return characterRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Character not found.")))
			.flatMap(e -> readAuthorizationFilter.apply(auth, e));
	}

	public Flux<CharacterInfo> findAll(JwtAuthenticationToken auth, Pageable pageable) {
		if (auth.getAuthorities().stream().map(e -> e.getAuthority()).filter(e -> e.equals(Role.ADMIN.getCode())).count() > 0) {
			return characterRepository.findAll(pageable.getSort());
		}
		return userService.findOrCreate(auth.getName())
			.map(this::filterOwners)
			.flatMapMany(ids -> characterRepository.findByOwner(ids, pageable.getSort()));
	}

	private List<String> filterOwners(User user) {
		List<String> check = new ArrayList<>();
		check.add(user.getId());
		check.addAll(user.getFriends());
		return check;
	}

}
