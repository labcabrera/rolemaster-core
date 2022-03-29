package org.labcabrera.rolemaster.core.service.character;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.ContextCharacterModifiers;
import org.labcabrera.rolemaster.core.model.tactical.ExhaustionPoints;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.PowerPoints;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterContextRepository;
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
	private TacticalCharacterContextRepository repository;

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
			.map(character -> createContext(character))
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

	private TacticalCharacterContext createContext(CharacterInfo character) {
		return TacticalCharacterContext.builder()
			.characterId(character.getId())
			.hp(Hp.builder()
				.max(character.getMaxHp())
				.current(character.getMaxHp())
				.build())
			.powerPoints(PowerPoints.builder()
				.max(0)
				.max(0)
				.build())
			.exhaustionPoints(ExhaustionPoints.builder()
				.max(character.getMaxExhaustionPoints())
				.current(character.getMaxExhaustionPoints())
				.build())
			.modifiers(ContextCharacterModifiers.builder()
				.initiative(character.getAttributes().get(AttributeType.QUICKNESS).getCurrentValue())
				.build())
			.build();
	}

}
