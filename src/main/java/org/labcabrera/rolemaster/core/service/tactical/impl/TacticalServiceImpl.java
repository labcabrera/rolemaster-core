package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.labcabrera.rolemaster.core.controller.converter.TacticalCharacterContextConverter;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRoundState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.actions.InitiativeModifier;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterContextRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalNpcInstanceService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalRoundService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Validated
public class TacticalServiceImpl implements TacticalService {

	@Autowired
	private TacticalSessionService tacticalSessionService;

	@Autowired
	private TacticalNpcInstanceService tacticalNpcInstanceService;

	@Autowired
	private TacticalRoundService tacticalRoundService;

	@Autowired
	private TacticalCharacterContextConverter tacticalCharacterContextConverter;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private TacticalRoundRepository tacticalRoundRepository;

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private TacticalCharacterContextRepository tacticalCharacterStatusRepository;

	@Autowired
	private CharacterInfoRepository characterInfoRepository;

	@Override
	public Mono<TacticalSession> createSession(TacticalSessionCreation request) {
		return tacticalSessionService.createSession(request);
	}

	@Override
	public Mono<TacticalCharacter> addCharacter(String tacticalSessionId, String characterId) {
		return tacticalSessionRepository
			.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Invalid tactical session id.")))
			.zipWith(characterInfoRepository.findById(characterId))
			.map(pair -> tacticalCharacterContextConverter.convert(pair.getT1(), pair.getT2()))
			.flatMap(tacticalCharacterStatusRepository::save);
	}

	@Override
	public Mono<TacticalCharacter> addNpc(String tacticalSessionId, String npcId) {
		return tacticalSessionRepository
			.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Invalid tactical session id.")))
			.zipWith(tacticalNpcInstanceService.create(npcId))
			.map(pair -> tacticalCharacterContextConverter.convert(pair.getT1(), pair.getT2()))
			.flatMap(tacticalCharacterStatusRepository::save);
	}

	@Override
	public Mono<TacticalRound> startRound(String tacticalSessionId) {
		return tacticalSessionRepository.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Invalid tactical session id.")))
			.map(e -> {
				TacticalRound round = TacticalRound.builder()
					//TODO read max
					.round(1)
					.state(TacticalRoundState.ACTION_DECLARATION)
					.tacticalSessionId(tacticalSessionId)
					.metadata(EntityMetadata.builder()
						.created(LocalDateTime.now())
						.build())
					.build();
				return round;
			})
			.flatMap(tacticalRoundRepository::save);
	}

	@Override
	public Mono<TacticalRound> getCurrentRound(String tacticalSessionId) {
		return tacticalRoundRepository.findFirstByTacticalSessionIdOrderByRoundDesc(tacticalSessionId);
	}

	@Override
	public Mono<TacticalRound> generateRandomInitiatives(String tacticalSessionId) {
		return getCurrentRound(tacticalSessionId)
			.map(tacticalRoundService::generateRandomInitiatives)
			.flatMap(tacticalRoundRepository::save);
	}

	@Override
	public Flux<TacticalAction> getActionQueue(String roundId) {
		return tacticalRoundRepository.findById(roundId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Missing round")))
			.map(round -> {
				if (round.getState() != TacticalRoundState.ACTION_RESOLUTION) {
					throw new BadRequestException("Invalid round state");
				}
				return round;
			})
			.thenMany(actionRepository.findByRoundIdOrderByEffectiveInitiativeDesc(roundId));
	}

	@Override
	public Mono<TacticalRound> startInitiativeDeclaration(String roundId) {
		return tacticalRoundRepository.findById(roundId)
			.map(round -> {
				if (round.getState() != TacticalRoundState.ACTION_DECLARATION) {
					throw new BadRequestException("Invalid round state");
				}
				round.setState(TacticalRoundState.INITIATIVE_DECLARATION);
				round.getMetadata().setUpdated(LocalDateTime.now());
				return round;
			})
			.flatMap(tacticalRoundRepository::save);
	}

	@Override
	public Mono<TacticalRound> setInitiative(String roundId, String character, Integer initiativeRoll) {
		return tacticalRoundRepository.findById(roundId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Round not found")))
			.map(action -> {
				action.getInitiativeRollMap().put(character, initiativeRoll);
				return action;
			})
			.flatMap(tacticalRoundRepository::save);
	}

	@Override
	public Mono<TacticalRound> startExecutionPhase(String roundId) {
		return tacticalRoundRepository.findById(roundId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Round not found")))
			.zipWith(actionRepository.findByRoundId(roundId).collectList())
			.map(pair -> {
				TacticalRound round = pair.getT1();
				List<TacticalAction> actions = pair.getT2();
				for (TacticalAction action : actions) {
					if (!round.getInitiativeRollMap().containsKey(action.getSource())) {
						throw new BadRequestException("Missing initiative for action " + action.getId());
					}
					Integer roll = round.getInitiativeRollMap().get(action.getSource());
					action.getInitiativeModifiers().put(InitiativeModifier.ROLL, roll);
					//TODO load all initiatives
					Integer effectiveInitiative = 11;
					action.setEffectiveInitiative(effectiveInitiative);

				}
				round.setState(TacticalRoundState.ACTION_RESOLUTION);
				round.getMetadata().setUpdated(LocalDateTime.now());
				return pair;
			})
			.flatMap(pair -> {
				return actionRepository.saveAll(pair.getT2())
					.then(tacticalRoundRepository.save(pair.getT1()));
			});
	}

}
