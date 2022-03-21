package org.labcabrera.rolemaster.core.service.session;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.character.Session;
import org.labcabrera.rolemaster.core.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class SessionService {

	@Autowired
	private SessionRepository repository;

	public Mono<Session> findById(String id) {
		return repository.findById(id);
	}

	public Flux<Session> findAll() {
		return repository.findAll();
	}

	public Mono<Session> createSession(String name) {
		Session session = Session.builder()
			.name(name)
			.metadata(EntityMetadata.builder()
				.created(LocalDateTime.now())
				.build())
			.build();
		return repository.save(session);
	}

	public Mono<Void> deleteAll() {
		log.info("Deleting all sessions");
		return repository.deleteAll();
	}

	public Mono<Void> deleteAll(String id) {
		log.info("Deleting session {}", id);
		return repository.deleteById(id);
	}

}
