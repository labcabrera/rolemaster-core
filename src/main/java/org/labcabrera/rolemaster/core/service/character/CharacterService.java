package org.labcabrera.rolemaster.core.service.character;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CharacterService {

	@Autowired
	private CharacterInfoRepository repositoty;

	public Mono<CharacterInfo> findById(String id) {
		return repositoty.findById(id);
	}

	public Flux<CharacterInfo> findAll(Pageable pageable) {
		return repositoty.findAll(pageable.getSort());
	}

	public Mono<Void> deleteById(String id) {
		return repositoty.deleteById(id);
	}

	public Mono<Void> deleteAll() {
		return repositoty.deleteAll();
	}

}
