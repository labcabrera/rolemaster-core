package org.labcabrera.rolemaster.core.repository;

import java.util.List;

import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface StrategicSessionRepository extends ReactiveMongoRepository<StrategicSession, String> {

	@Query("{ 'owner': {$in: ?0 }}")
	Flux<StrategicSession> findByOwner(List<String> owner, Sort pageable);
}
