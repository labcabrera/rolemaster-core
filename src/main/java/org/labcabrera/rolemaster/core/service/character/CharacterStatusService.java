package org.labcabrera.rolemaster.core.service.character;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.labcabrera.rolemaster.core.repository.CharacterStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
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
		CharacterInfo character = characterService.findById(characterId).share().block();
		CharacterStatus status = CharacterStatus.builder()
			.sessionId(sessionId)
			.characterId(characterId)
			.hp(character.getMaxHp())
			.build();
		return repository.save(status);
	}

	public Mono<Void> deleteById(String id) {
		return repository.deleteById(id);
	}

	public Mono<Void> deleteAll() {
		return repository.deleteAll();
	}

}
