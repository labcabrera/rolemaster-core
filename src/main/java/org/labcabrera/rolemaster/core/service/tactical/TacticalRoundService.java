package org.labcabrera.rolemaster.core.service.tactical;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.actions.InitiativeModifier;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalRoundService {

	@Autowired
	private RandomService randomService;

	@Autowired
	private TacticalRoundRepository repository;

	@Autowired
	private TacticalCharacterContextService contextService;

	public List<TacticalAction> getQueue(TacticalRound round) {
		if (!round.getInitiativeLoaded()) {
			throw new BadRequestException("Initiatives have not been loaded");
		}
		return round.getActions();
	}

	public TacticalRound generateRandomInitiatives(TacticalRound round) {
		Set<String> characters = getCharacters(round);
		characters.stream().forEach(c -> {
			if (!round.getInitiativeRollMap().containsKey(c)) {
				round.getInitiativeRollMap().put(c, randomService.d10() + randomService.d10());
			}
		});
		return round;
	}

	public Set<String> getCharacters(TacticalRound round) {
		Set<String> set = new HashSet<>();
		for (TacticalAction action : round.getActions()) {
			set.add(action.getSource());
		}
		return set;
	}

	public Mono<TacticalRound> loadInitiatives(TacticalRound round) {
		Set<String> characters = getCharacters(round);
		return repository.findById(round.getId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Invalid tactical round")))
			.zipWith(contextService.getStatusAsList(characters))
			.map(pair -> loadInitiatives(pair.getT1(), pair.getT2()))
			.map(e -> {
				e.getMetadata().setUpdated(LocalDateTime.now());
				return e;
			}).flatMap(repository::save);
	}

	private TacticalRound loadInitiatives(TacticalRound round, List<TacticalCharacterContext> characters) {
		characters.stream().forEach(c -> {
			String characterIdentifier = c.getCharacterId();
			Map<InitiativeModifier, Integer> map = new LinkedHashMap<>();
			map.put(InitiativeModifier.RANDOM_ROLL, round.getInitiativeRollMap().get(characterIdentifier));
			map.put(InitiativeModifier.DECLARED_MOVEMENT, 0);
			map.put(InitiativeModifier.HP, 0);
			map.put(InitiativeModifier.SURPRISED, 0);
			map.put(InitiativeModifier.ATTRIBUTE, 0);
			map.put(InitiativeModifier.CUSTOM, 0);
			round.getInitiatives().put(characterIdentifier, map);
		});
		return round;
	}

}
