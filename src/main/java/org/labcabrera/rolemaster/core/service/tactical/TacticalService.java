package org.labcabrera.rolemaster.core.service.tactical;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.TacticalSessionCreationRequest;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalService {

	@Autowired
	private TacticalSessionService tacticalSessionService;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private TacticalRoundRepository tacticalRoundRepository;

	public Mono<TacticalSession> createSession(TacticalSessionCreationRequest request) {
		return tacticalSessionService.createSession(request);
	}

	public Mono<TacticalRound> startRound(String tacticalSessionId) {
		return tacticalSessionRepository.findById(tacticalSessionId)
			.map(e -> {
				e.setCurrentRound(e.getCurrentRound() + 1);
				return e;
			})
			.flatMap(tacticalSessionRepository::save)
			.map(e -> {
				TacticalRound round = TacticalRound.builder()
					.tacticalSessionId(tacticalSessionId)
					.round(e.getCurrentRound())
					.metadata(EntityMetadata.builder()
						.created(LocalDateTime.now())
						.build())
					.build();
				return round;
			})
			.flatMap(tacticalRoundRepository::save);
	}

	public Mono<TacticalRound> declare(String tacticalSessionId, TacticalAction action) {
		return getCurrentRound(tacticalSessionId)
			.map(e -> {
				//TODO validation
				e.getActions().add(action);
				return e;
			})
			.flatMap(tacticalRoundRepository::save);
	}

	public Mono<TacticalSession> setInitiative(String sessionid, String characterId, int i) {
		return null;
	}

	public Mono<TacticalRound> getCurrentRound(String tacticalSessionId) {
		return tacticalRoundRepository.findByTacticalSessionIdOrderByRoundDesc(tacticalSessionId);
	}

}
