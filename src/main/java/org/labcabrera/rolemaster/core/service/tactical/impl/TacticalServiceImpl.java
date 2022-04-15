package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.labcabrera.rolemaster.core.converter.TacticalCharacterContextConverter;
import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRoundState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSessionLog;
import org.labcabrera.rolemaster.core.model.tactical.action.InitiativeModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.NpcRepository;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionLogRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalNpcCharacterService;
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
	private TacticalNpcCharacterService npcCharacterService;

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
	private TacticalCharacterRepository tacticalCharacterRepository;

	@Autowired
	private CharacterInfoRepository characterInfoRepository;

	@Autowired
	private TacticalSessionLogRepository tacticalSessionLogRepository;

	@Autowired
	private NpcRepository npcRepository;

	@Autowired
	private TacticalSessionLogRepository logRepository;

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
			.flatMap(tacticalCharacterRepository::save);
	}

	@Override
	public Mono<TacticalCharacter> addNpc(String tacticalSessionId, String npcId) {
		return addNpc(tacticalSessionId, npcId, null);
	}

	@Override
	public Mono<TacticalCharacter> addNpc(String tacticalSessionId, String npcId, NpcCustomization npcCustomization) {
		return tacticalSessionRepository
			.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Invalid tactical session id.")))
			.zipWith(npcRepository.findById(npcId))
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Invalid NPC identifier " + npcId)))
			.flatMap(pair -> npcCharacterService.create(tacticalSessionId, pair.getT2(), npcCustomization))
			.flatMap(tacticalCharacterRepository::save);
	}

	@Override
	public Mono<TacticalRound> startRound(String tacticalSessionId) {
		return tacticalSessionRepository.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Invalid tactical session id.")))
			.map(e -> {
				TacticalRound round = TacticalRound.builder()
					.state(TacticalRoundState.ACTION_DECLARATION)
					.tacticalSessionId(tacticalSessionId)
					.metadata(EntityMetadata.builder()
						.created(LocalDateTime.now())
						.build())
					.build();
				return round;
			})
			.zipWith(tacticalRoundRepository
				.findFirstByTacticalSessionIdOrderByRoundDesc(tacticalSessionId)
				.switchIfEmpty(Mono.just(TacticalRound.builder().round(1).build())), (a, b) -> {
					a.setRound(b.getRound() + 1);
					return a;
				})
			.flatMap(tacticalRoundRepository::save);
	}

	@Override
	public Mono<TacticalRound> getCurrentRound(String tacticalSessionId) {
		return tacticalRoundRepository
			.findFirstByTacticalSessionIdOrderByRoundDesc(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical round not found for session " + tacticalSessionId)));
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
			.flatMap(tacticalRoundRepository::save)
			.flatMap(e -> this.<TacticalRound>log(e, e.getTacticalSessionId(), e.getRound(), "Starting initiative declaration"));
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
				List<TacticalAction> actions = pair.getT2();
				TacticalRound round = pair.getT1();
				for (TacticalAction action : actions) {
					if (!round.getInitiativeRollMap().containsKey(action.getSource())) {
						throw new BadRequestException("Missing initiative declaration for character " + action.getSource());
					}
				}
				return pair;
			})
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

	//TODO delete actions
	@Override
	public Mono<Void> deleteSession(String tacticalSessionId) {
		return tacticalSessionRepository.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session not found.")))
			.then(tacticalRoundRepository.deleteByTacticalSessionId(tacticalSessionId))
			.then(tacticalCharacterRepository.deleteByTacticalSessionId(tacticalSessionId))
			.then(tacticalSessionLogRepository.deleteByTacticalSessionId(tacticalSessionId))
			.then(tacticalSessionRepository.deleteById(tacticalSessionId));
	}

	private <E> Mono<E> log(E e, String tacticalSessionId, Integer round, String message) {
		TacticalSessionLog logEntry = TacticalSessionLog.builder()
			.tacticalSessionId(tacticalSessionId)
			.message(message)
			.round(round)
			.created(LocalDateTime.now())
			.build();
		return logRepository.insert(logEntry).map(entry -> e);
	}

}
