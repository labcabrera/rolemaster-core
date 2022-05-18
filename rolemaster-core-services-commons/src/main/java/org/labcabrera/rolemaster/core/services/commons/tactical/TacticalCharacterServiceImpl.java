package org.labcabrera.rolemaster.core.services.commons.tactical;

import java.util.List;
import java.util.Set;

import org.labcabrera.rolemaster.core.dto.tactical.TacticalCharacterModification;
import org.labcabrera.rolemaster.core.model.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalNpcInstanceRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.services.commons.converter.CharacterInfoTacticalCharacterConverter;
import org.labcabrera.rolemaster.core.services.tactical.TacticalCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
class TacticalCharacterServiceImpl implements TacticalCharacterService {

	@Autowired
	private TacticalCharacterRepository repository;

	@Autowired
	private TacticalNpcInstanceRepository npcInstanceRepository;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private CharacterInfoRepository characterInfoRepository;

	@Autowired
	private CharacterInfoTacticalCharacterConverter converter;

	//	@Autowired
	//	private ReadAuthorizationFilter readAuthFilter;

	@Override
	public Mono<TacticalCharacter> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Flux<TacticalCharacter> findAll(Pageable pageable) {
		return repository.findAll(pageable.getSort());
	}

	@Override
	public Mono<TacticalCharacter> create(String tacticalSessionId, String characterId) {
		return Mono.zip(tacticalSessionRepository.findById(tacticalSessionId), characterInfoRepository.findById(characterId))
			.flatMap(tuple -> converter.convert(tuple.getT1(), tuple.getT2()))
			.flatMap(repository::save);
	}

	@Override
	public Mono<TacticalCharacter> update(JwtAuthenticationToken auth, String tacticalCharacterId,
		TacticalCharacterModification modification) {
		return repository.findById(tacticalCharacterId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical character not found.")))
			//TODO auth
			//.flatMap(tc -> readAuthFilter.apply(auth, tc))
			.map(tc -> {
				if (modification.getHp() != null) {
					tc.getHp().setCurrent(modification.getHp());
				}
				return tc;
			})
			.flatMap(repository::save);
	}

	@Override
	public Mono<Void> delete(String id) {
		return repository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical character context not found")))
			.flatMap(ctx -> {
				if (ctx.isNpc()) {
					return npcInstanceRepository.deleteById(ctx.getCharacterId()).then(Mono.just(ctx));
				}
				else {
					return Mono.just(ctx);
				}
			})
			.flatMap(repository::delete);
	}

	@Override
	public Mono<List<TacticalCharacter>> getStatusAsList(Set<String> characterIdentifiers) {
		return repository.findByCharacterId(characterIdentifiers).collectList();
	}

}
