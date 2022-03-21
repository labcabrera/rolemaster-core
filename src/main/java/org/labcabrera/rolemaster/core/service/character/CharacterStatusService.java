package org.labcabrera.rolemaster.core.service.character;

import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.labcabrera.rolemaster.core.repository.CharacterStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CharacterStatusService {

	@Autowired
	private CharacterStatusRepository repository;

	@Autowired
	private CharacterService characterService;

	public Mono<CharacterStatus> findById(String id) {
		return repository.findById(id);
	}

	public Flux<CharacterStatus> findAll() {
		return repository.findAll();
	}

	public Mono<CharacterStatus> create(String sessionId, String characterId) {
		return characterService.findById(characterId)
			.doOnNext(character -> log.info("Readed person {}", character))
			.map(character -> {
				return CharacterStatus.builder()
					.sessionId(sessionId)
					.characterId(characterId)
					.hp(character.getMaxHp())
					.build();
			})
			.doOnNext(status -> {
				log.info("Created status {}", status);
			})
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
