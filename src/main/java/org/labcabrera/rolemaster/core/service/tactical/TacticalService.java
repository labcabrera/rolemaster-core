package org.labcabrera.rolemaster.core.service.tactical;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.TacticalSessionCreationRequest;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.model.tactical.TacticalNpcInstance;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterStatusRepository;
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

	@Autowired
	private TacticalCharacterStatusRepository tacticalCharacterStatusRepository;

	@Autowired
	private TacticalNpcInstanceService tacticalNpcInstanceService;

	public Mono<TacticalSession> createSession(TacticalSessionCreationRequest request) {
		return tacticalSessionService.createSession(request);
	}

	public Mono<TacticalCharacterContext> addCharacter(String tacticalSessionId, String characterId) {
		return tacticalSessionRepository
			.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Invalid tactical session id.")))
			.map(tacticalSession -> {
				return TacticalCharacterContext.builder()
					.tacticalSessionId(tacticalSessionId)
					.characterId(characterId)
					.metadata(EntityMetadata.builder().build())
					.build();
			})
			.flatMap(tacticalCharacterStatusRepository::save);
	}

	public Mono<TacticalCharacterContext> addNpc(String tacticalSessionId, String npcId) {
		return tacticalSessionRepository
			.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Invalid tactical session id.")))
			.zipWith(tacticalNpcInstanceService.create(npcId))
			.map(pair -> {
				TacticalSession tacticalSession = pair.getT1();
				TacticalNpcInstance npcInstance = pair.getT2();
				return TacticalCharacterContext.builder()
					.tacticalSessionId(tacticalSession.getId())
					.npcInstanceId(npcInstance.getId())
					.metadata(EntityMetadata.builder().build())
					.build();
			})
			.flatMap(tacticalCharacterStatusRepository::save);
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
