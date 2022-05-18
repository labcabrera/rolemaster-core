package org.labcabrera.rolemaster.core.services.rmss.tactical;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.repository.NpcRepository;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionLogRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.services.commons.Messages.Errors;
import org.labcabrera.rolemaster.core.services.tactical.TacticalCharacterService;
import org.labcabrera.rolemaster.core.services.tactical.TacticalNpcCharacterService;
import org.labcabrera.rolemaster.core.services.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Validated
public class TacticalServiceImpl implements TacticalService {

	@Autowired
	private TacticalSessionCreationService tacticalSessionService;

	@Autowired
	private TacticalNpcCharacterService npcCharacterService;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private TacticalRoundRepository tacticalRoundRepository;

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	@Autowired
	private TacticalSessionLogRepository tacticalSessionLogRepository;

	@Autowired
	private NpcRepository npcRepository;

	@Autowired
	private TacticalLogService logService;

	@Autowired
	private EndTurnCharacterProcessor endTurnCharacterProcessor;

	@Autowired
	private TacticalCharacterService tacticalCharacterService;

	@Override
	public Mono<TacticalSession> createSession(JwtAuthenticationToken auth, TacticalSessionCreation request) {
		return tacticalSessionService.createSession(auth, request);
	}

	@Override
	public Mono<TacticalCharacter> addCharacter(String tacticalSessionId, String characterId) {
		return tacticalCharacterService.create(tacticalSessionId, characterId);
	}

	@Override
	public Mono<TacticalCharacter> addNpc(String tacticalSessionId, String npcId) {
		return addNpc(tacticalSessionId, npcId, null);
	}

	@Override
	public Mono<TacticalCharacter> addNpc(String tacticalSessionId, String npcId, NpcCustomization npcCustomization) {
		return tacticalSessionRepository
			.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException(Errors.tacticalSessionNotFound(tacticalSessionId))))
			.zipWith(npcRepository.findById(npcId))
			.switchIfEmpty(Mono.error(() -> new BadRequestException(Errors.invalidNpcId(npcId))))
			.flatMap(pair -> npcCharacterService.create(tacticalSessionId, pair.getT2(), npcCustomization))
			.flatMap(tacticalCharacterRepository::save);
	}

	@Override
	public Mono<TacticalRound> startRound(String tacticalSessionId) {
		return tacticalSessionRepository.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException(Errors.tacticalSessionNotFound(tacticalSessionId))))
			.flatMap(session -> endTurnCharacterProcessor.process(session, tacticalSessionId))
			.map(e -> TacticalRound.builder()
				.tacticalSessionId(tacticalSessionId)
				.metadata(EntityMetadata.builder()
					.created(LocalDateTime.now())
					.build())
				.build())
			.zipWith(tacticalRoundRepository
				.findFirstByTacticalSessionIdOrderByRoundDesc(tacticalSessionId)
				.switchIfEmpty(Mono.just(TacticalRound.builder().round(1).build())), (a, b) -> {
					a.setRound(b.getRound() + 1);
					return a;
				})
			.flatMap(tacticalRoundRepository::save)
			.flatMap(e -> logService.startRound(e, tacticalSessionId, e.getRound()));
	}

	@Override
	public Mono<TacticalRound> getCurrentRound(String tacticalSessionId) {
		return tacticalRoundRepository
			.findFirstByTacticalSessionIdOrderByRoundDesc(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.tacticalSessionHasNoRound(tacticalSessionId))));
	}

	@Override
	public Flux<TacticalAction> getActionQueue(String roundId) {
		return tacticalRoundRepository.findById(roundId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException(Errors.roundNotFound(roundId))))
			.thenMany(actionRepository.findByRoundId(roundId));
	}

	//TODO delete actions
	@Override
	public Mono<Void> deleteSession(String tacticalSessionId) {
		return tacticalSessionRepository.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.tacticalSessionNotFound(tacticalSessionId))))
			.then(tacticalRoundRepository.deleteByTacticalSessionId(tacticalSessionId))
			.then(tacticalCharacterRepository.deleteByTacticalSessionId(tacticalSessionId))
			.then(tacticalSessionLogRepository.deleteByTacticalSessionId(tacticalSessionId))
			.then(tacticalSessionRepository.deleteById(tacticalSessionId))
			.then(logService.deleteByTacticalSessionId(tacticalSessionId));
	}

}
