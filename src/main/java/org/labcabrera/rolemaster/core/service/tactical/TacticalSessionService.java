package org.labcabrera.rolemaster.core.service.tactical;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSessionState;
import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalSessionService {

	@Autowired
	private StrategicSessionRepository sessionRepository;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	public Mono<TacticalSession> createSession(TacticalSessionCreation request) {
		return sessionRepository
			.findById(request.getStrategicSessionId())
			.switchIfEmpty(Mono.error(new BadRequestException("Invalid sessionId")))
			.map(session -> TacticalSession.builder()
				.strategicSessionId(session.getId())
				.name(request.getName())
				.description(request.getDescription())
				.state(TacticalSessionState.CREATED)
				.entityMetadata(EntityMetadata.builder()
					.created(LocalDateTime.now())
					.build())
				.build())
			.flatMap(tacticalSessionRepository::insert);
	}

}
