package org.labcabrera.rolemaster.core.service.tactical;

import java.util.List;
import java.util.Set;

import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.ContextCharacterModifiers;
import org.labcabrera.rolemaster.core.model.tactical.ExhaustionPoints;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.PowerPoints;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterContextRepository;
import org.labcabrera.rolemaster.core.repository.TacticalNpcInstanceRepository;
import org.labcabrera.rolemaster.core.service.character.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TacticalCharacterContextService {

	@Autowired
	private TacticalCharacterContextRepository repository;

	@Autowired
	private TacticalNpcInstanceRepository npcInstanceRepository;

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

	public Mono<Void> delete(String id) {
		return repository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical character context not found")))
			.flatMap(ctx -> {
				if (ctx.getIsNpc()) {
					return npcInstanceRepository.deleteById(ctx.getCharacterId()).then(Mono.just(ctx));
				}
				else {
					return Mono.just(ctx);
				}
			})
			.flatMap(repository::delete);
	}

	public Mono<List<TacticalCharacterContext>> getStatusAsList(Set<String> characterIdentifiers) {
		return repository.findByCharacterId(characterIdentifiers).collectList();
	}

	public Mono<Void> deleteAll() {
		return repository.deleteAll();
	}

	private TacticalCharacterContext createContext(CharacterInfo character) {
		return TacticalCharacterContext.builder()
			.characterId(character.getId())
			.name(character.getName())
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
