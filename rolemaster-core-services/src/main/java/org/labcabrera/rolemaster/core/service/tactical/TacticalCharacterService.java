package org.labcabrera.rolemaster.core.service.tactical;

import java.util.List;
import java.util.Set;

import org.labcabrera.rolemaster.core.converter.CharacterInfoTacticalCharacterConverter;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalNpcInstanceRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TacticalCharacterService {

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

	public Mono<TacticalCharacter> findById(String id) {
		return repository.findById(id);
	}

	public Flux<TacticalCharacter> findAll(Pageable pageable) {
		return repository.findAll(pageable.getSort());
	}

	public Mono<TacticalCharacter> create(String tacticalSessionId, String characterId) {
		return Mono.zip(tacticalSessionRepository.findById(tacticalSessionId), characterInfoRepository.findById(characterId))
			.flatMap(tuple -> converter.convert(tuple.getT1(), tuple.getT2()))
			.flatMap(repository::save);
	}

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

	public Mono<List<TacticalCharacter>> getStatusAsList(Set<String> characterIdentifiers) {
		return repository.findByCharacterId(characterIdentifiers).collectList();
	}

	public Mono<Void> deleteAll() {
		return repository.deleteAll();
	}

}
