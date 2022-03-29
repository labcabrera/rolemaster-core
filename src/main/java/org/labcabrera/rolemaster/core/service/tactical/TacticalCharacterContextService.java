package org.labcabrera.rolemaster.core.service.tactical;

import java.util.List;
import java.util.Set;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TacticalCharacterContextService {

	@Autowired
	private TacticalCharacterContextRepository repository;

	Flux<TacticalCharacterContext> getStatus(Set<String> characterIdentifiers) {
		return repository.findByCharacterId(characterIdentifiers);
	}

	Mono<List<TacticalCharacterContext>> getStatusAsList(Set<String> characterIdentifiers) {
		return repository.findByCharacterId(characterIdentifiers).collectList();
	}

}
