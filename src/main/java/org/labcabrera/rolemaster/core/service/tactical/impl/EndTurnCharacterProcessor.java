package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.model.tactical.Buff;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class EndTurnCharacterProcessor {

	@Autowired
	private TacticalCharacterRepository characterRepository;

	public <E> Mono<E> process(E data, String tacticalSessionId) {
		return characterRepository.findByTacticalSessionId(tacticalSessionId)
			.map(this::processDeath)
			.map(this::processBleeding)
			.map(this::processBuffs)
			.map(this::processDebuffs)
			.map(this::processBonus)
			.map(this::processPenalty)
			.flatMap(characterRepository::save)
			.collectList()
			.then(Mono.just(data));
	}

	private TacticalCharacter processDeath(TacticalCharacter tc) {
		//TODO
		return tc;
	}

	private TacticalCharacter processBleeding(TacticalCharacter tc) {
		if (tc.getCombatStatus().getTotalBleeding() > 0) {
			tc.getHp().add(-tc.getCombatStatus().getTotalBleeding());
		}
		return tc;
	}

	private TacticalCharacter processBuffs(TacticalCharacter tc) {
		tc.getCombatStatus().getBuffs().entrySet().stream()
			.filter(e -> e.getValue() > 0)
			.map(e -> e.setValue(e.getValue() - 1));
		for (Entry<Buff, Integer> buff : tc.getCombatStatus().getBuffs().entrySet()) {
			if (buff.getValue() == 0) {
				tc.getCombatStatus().getBuffs().remove(buff.getKey());
			}
		}
		return tc;
	}

	private TacticalCharacter processDebuffs(TacticalCharacter tc) {
		tc.getCombatStatus().getDebuffs().entrySet().stream()
			.filter(e -> e.getValue() > 0)
			.map(e -> e.setValue(e.getValue() - 1));
		for (Entry<Debuff, Integer> buff : tc.getCombatStatus().getDebuffs().entrySet()) {
			if (buff.getValue() == 0) {
				tc.getCombatStatus().getDebuffs().remove(buff.getKey());
			}
		}
		return tc;
	}

	private TacticalCharacter processBonus(TacticalCharacter tc) {
		//TODO
		return tc;
	}

	private TacticalCharacter processPenalty(TacticalCharacter tc) {
		//TODO
		return tc;
	}

}
