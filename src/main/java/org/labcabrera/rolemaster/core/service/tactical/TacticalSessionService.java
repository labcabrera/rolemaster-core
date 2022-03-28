package org.labcabrera.rolemaster.core.service.tactical;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.TacticalSessionCreationRequest;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.repository.SessionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalSessionService {

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	public Mono<TacticalSession> createSession(TacticalSessionCreationRequest request) {
		return sessionRepository
			.findById(request.getSessionId())
			.switchIfEmpty(Mono.error(new BadRequestException("Invalid sessionId")))
			.map(session -> {
				return TacticalSession.builder()
					.sessionId(session.getId())
					.name(request.getName())
					.entityMetadata(EntityMetadata.builder()
						.created(LocalDateTime.now())
						.build())
					.build();
			})
			.flatMap(tacticalSessionRepository::insert);
	}

}
