package org.labcabrera.rolemaster.core.service.tactical;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRoundPhase;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TacticalRoundService {

	@Autowired
	private RandomService randomService;

	public List<TacticalAction> getQueue(TacticalRound round) {
		if (round.getPhase() == TacticalRoundPhase.ACTION_DECLARATION_PHASE) {

		}
		switch (round.getPhase()) {
		case ACTION_DECLARATION_PHASE:
			throw new BadRequestException("Initiatives have not yet been declared");
		case INITIATIVE_DETERMINATION_PHASE:
			if (!checkInitiatives(round)) {
				throw new BadRequestException("Initiatives have not been declared");
			}
			break;
		default:
			break;
		}
		//TODO
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

	private boolean checkInitiatives(TacticalRound round) {
		return false;
	}

}
