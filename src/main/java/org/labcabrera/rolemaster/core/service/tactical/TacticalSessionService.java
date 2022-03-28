package org.labcabrera.rolemaster.core.service.tactical;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalSessionService {

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	public Mono<TacticalSession> createSession(String sessionId) {
		TacticalSession session = TacticalSession.builder()
			.entityMetadata(EntityMetadata.builder()
				.created(LocalDateTime.now())
				.build())
			.build();
		return tacticalSessionRepository.insert(session);
	}

}
