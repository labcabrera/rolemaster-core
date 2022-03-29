package org.labcabrera.rolemaster.core.service.character;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CharacterTacticalContextService {

	@Autowired
	private TacticalCharacterStatusRepository repository;

	@Autowired
	private CharacterService characterService;

	public Mono<TacticalCharacterContext> findById(String id) {
		return repository.findById(id);
	}

	public Flux<TacticalCharacterContext> findAll(Pageable pageable) {
		return repository.findAll(pageable.getSort());
	}

	public Mono<TacticalCharacterContext> create(String sessionId, String characterId) {
		return characterService.findById(characterId)
			.doOnNext(character -> log.info("Readed person {}", character))
			.map(character -> TacticalCharacterContext.builder()
				.characterId(characterId)
				.hp(character.getMaxHp())
				.build())
			.doOnNext(status -> log.info("Created status {}", status))
			.flatMap(repository::save)
			.doOnNext(status -> log.info("Saved status {}", status));
	}

	public Mono<Void> deleteById(String id) {
		return repository.deleteById(id);
	}

	public Mono<Void> deleteAll() {
		return repository.deleteAll();
	}

}
