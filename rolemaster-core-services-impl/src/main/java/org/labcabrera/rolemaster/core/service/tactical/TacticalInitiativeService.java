package org.labcabrera.rolemaster.core.service.tactical;

import java.time.LocalDateTime;
import java.util.List;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRoundState;
import org.labcabrera.rolemaster.core.model.tactical.action.InitiativeModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.service.Messages.Errors;
import org.labcabrera.rolemaster.core.service.Messages.TacticalLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalInitiativeService {

	@Autowired
	private TacticalRoundRepository tacticalRoundRepository;

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private TacticalLogService logService;

	public Mono<TacticalRound> startInitiativeDeclaration(String roundId) {
		return tacticalRoundRepository.findById(roundId)
			.map(round -> {
				if (round.getState() != TacticalRoundState.ACTION_DECLARATION) {
					throw new BadRequestException(Errors.INVALID_ROUND_STATE);
				}
				round.setState(TacticalRoundState.INITIATIVE_DECLARATION);
				round.getMetadata().setUpdated(LocalDateTime.now());
				return round;
			})
			.flatMap(tacticalRoundRepository::save)
			.flatMap(e -> logService.log(e, e.getTacticalSessionId(), e.getRound(), TacticalLogs.START_INITIATIVE_DECLARATION));
	}

	public Mono<TacticalRound> setInitiative(String roundId, String character, Integer initiativeRoll) {
		return tacticalRoundRepository.findById(roundId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException(Errors.roundNotFound(roundId))))
			.map(action -> {
				action.getInitiativeRollMap().put(character, initiativeRoll);
				return action;
			})
			.flatMap(tacticalRoundRepository::save);
	}

	public Mono<TacticalRound> loadActionInitiatives(TacticalRound tr) {
		return Mono.just(tr)
			.zipWith(actionRepository.findByRoundId(tr.getId()).collectList())
			.map(pair -> {
				List<TacticalAction> actions = pair.getT2();
				TacticalRound round = pair.getT1();
				for (TacticalAction action : actions) {
					if (!round.getInitiativeRollMap().containsKey(action.getSource())) {
						throw new BadRequestException(Errors.missingChracterInitiative(action.getSource()));
					}
				}
				return pair;
			})
			.map(pair -> {
				TacticalRound round = pair.getT1();
				List<TacticalAction> actions = pair.getT2();
				for (TacticalAction action : actions) {
					if (!round.getInitiativeRollMap().containsKey(action.getSource())) {
						throw new BadRequestException(Errors.missingActionInitiative(action.getId()));
					}
					Integer roll = round.getInitiativeRollMap().get(action.getSource());
					action.getInitiativeModifiers().put(InitiativeModifier.ROLL, roll);
					if (round.getInitiativeModifiersMap().containsKey(action.getSource())) {
						action.getInitiativeModifiers().put(InitiativeModifier.CUSTOM, roll);
					}
					//TODO read from character
				}
				round.setState(TacticalRoundState.ACTION_RESOLUTION);
				round.getMetadata().setUpdated(LocalDateTime.now());
				return pair;
			})
			.flatMap(pair -> actionRepository.saveAll(pair.getT2()).then(tacticalRoundRepository.save(pair.getT1())));
	}
}
