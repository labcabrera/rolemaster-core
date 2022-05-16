package org.labcabrera.rolemaster.core.service.character;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.model.User;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.exception.NotFoundException;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.service.Messages.Errors;
import org.labcabrera.rolemaster.core.service.security.AuthorizationConsumer;
import org.labcabrera.rolemaster.core.service.security.ReadAuthorizationFilter;
import org.labcabrera.rolemaster.core.service.security.Role;
import org.labcabrera.rolemaster.core.service.security.WriteAuthorizationFilter;
import org.labcabrera.rolemaster.core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CharacterInfoServiceImpl implements CharacterInfoService {

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

	@Override
	public Mono<CharacterInfo> insert(JwtAuthenticationToken auth, CharacterInfo character) {
		authorizationConsumer.accept(auth, character);
		return characterRepository.save(character);
	}

	@Override
	public Mono<CharacterInfo> update(JwtAuthenticationToken auth, CharacterInfo character) {
		return characterRepository.findById(character.getId())
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterNotFound(character.getId()))))
			.map(e -> writeAuthorizationFilter.apply(auth, e))
			.then(characterRepository.save(character));
	}

	@Override
	public Mono<CharacterInfo> findById(JwtAuthenticationToken auth, String id) {
		return characterRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterNotFound(id))))
			.flatMap(e -> readAuthorizationFilter.apply(auth, e));
	}

	@Override
	public Flux<CharacterInfo> findAll(JwtAuthenticationToken auth, Pageable pageable) {
		if (auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(e -> e.equals(Role.ADMIN.getCode())).count() > 0) {
			return characterRepository.findAll(pageable.getSort());
		}
		return userService.findOrCreate(auth.getName())
			.map(this::filterOwners)
			.flatMapMany(ids -> characterRepository.findByOwner(ids, pageable.getSort()));
	}

	@Override
	public Mono<Void> deleteById(JwtAuthenticationToken auth, String id) {
		return characterRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterNotFound(id))))
			.map(e -> writeAuthorizationFilter.apply(auth, e))
			.then(characterRepository.deleteById(id));
	}

	private List<String> filterOwners(User user) {
		List<String> check = new ArrayList<>();
		check.add(user.getId());
		check.addAll(user.getFriends());
		return check;
	}

}
