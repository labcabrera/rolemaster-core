package org.labcabrera.rolemaster.core.repository;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CharacterInfoRepository extends ReactiveMongoRepository<CharacterInfo, String> {

	Mono<CharacterInfo> findByNameAndOwner(String id, String owner);

	@Query("{ 'owner': {$in: ?0 }}")
	Flux<CharacterInfo> findByOwner(List<String> owner, Sort pageable);

}
