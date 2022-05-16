package org.labcabrera.rolemaster.core.services.rmss.tactical;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.InitiativeModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.services.commons.RandomService;
import org.labcabrera.rolemaster.core.services.tactical.TacticalCharacterService;
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
	private TacticalCharacterService contextService;

	public List<TacticalAction> getQueue(TacticalRound round) {
		throw new NotImplementedException();
		//		if (!round.getInitiativeLoaded()) {
		//			throw new BadRequestException("Initiatives have not been loaded");
		//		}
		//		return round.getActions();
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
		throw new NotImplementedException();
		//		Set<String> set = new HashSet<>();
		//		for (TacticalAction action : round.getActions()) {
		//			set.add(action.getSource());
		//		}
		//		return set;
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

	private TacticalRound loadInitiatives(TacticalRound round, List<TacticalCharacter> characters) {
		characters.stream().forEach(c -> {
			String characterIdentifier = c.getCharacterId();
			Map<InitiativeModifier, Integer> map = new LinkedHashMap<>();
			map.put(InitiativeModifier.ROLL, round.getInitiativeRollMap().get(characterIdentifier));
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
