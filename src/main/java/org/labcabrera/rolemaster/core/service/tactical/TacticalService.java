package org.labcabrera.rolemaster.core.service.tactical;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.model.tactical.TacticalNpcInstance;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterContextRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;

@Service
@Validated
public class TacticalService {

	@Autowired
	private TacticalSessionService tacticalSessionService;

	@Autowired
	private TacticalNpcInstanceService tacticalNpcInstanceService;

	@Autowired
	private TacticalRoundService tacticalRoundService;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private TacticalRoundRepository tacticalRoundRepository;

	@Autowired
	private TacticalCharacterContextRepository tacticalCharacterStatusRepository;

	public Mono<TacticalSession> createSession(TacticalSessionCreation request) {
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
					.characterId(npcInstance.getId())
					.isNpc(true)
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

	@Validated
	public Mono<TacticalRound> declare(@NotEmpty String tacticalSessionId, @Valid TacticalAction action) {
		return getCurrentRound(tacticalSessionId)
			.map(e -> {
				e.getActions().add(action);
				return e;
			})
			.flatMap(tacticalRoundRepository::save);
	}

	public Mono<TacticalRound> getCurrentRound(String tacticalSessionId) {
		return tacticalRoundRepository.findByTacticalSessionIdOrderByRoundDesc(tacticalSessionId);
	}

	public Mono<TacticalRound> setInitiatives(String tacticalSessionId, Map<String, Integer> initiatives) {
		return tacticalSessionRepository
			.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Invalid tactical session identifier " + tacticalSessionId + ".")))
			.map(e -> e.getId())
			.flatMap(this::getCurrentRound)
			.map(tr -> {
				tr.getInitiativeRollMap().putAll(initiatives);
				return tr;
			})
			.flatMap(tacticalRoundRepository::save);
	}

	public Mono<TacticalRound> generateRandomInitiatives(String tacticalSessionId) {
		return getCurrentRound(tacticalSessionId)
			.map(tacticalRoundService::generateRandomInitiatives)
			.flatMap(tacticalRoundRepository::save);
	}

	public Mono<List<TacticalAction>> getActionQueue(String tacticalSessionId) {
		return getCurrentRound(tacticalSessionId).map(tacticalRoundService::getQueue);
	}

}
