package org.labcabrera.rolemaster.core.services.rmss.tactical;

import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.model.combat.Penalty;
import org.labcabrera.rolemaster.core.model.tactical.AttackBonus;
import org.labcabrera.rolemaster.core.model.tactical.Buff;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterState;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
class EndTurnCharacterProcessor {

	@Autowired
	private TacticalCharacterRepository characterRepository;

	public <E> Mono<E> process(E data, String tacticalSessionId) {
		return characterRepository.findByTacticalSessionId(tacticalSessionId)
			.map(this::processBleeding)
			.map(this::processBuffs)
			.map(this::processDebuffs)
			.map(this::processBonus)
			.map(this::processPenalty)
			.map(this::processDeath)
			.flatMap(characterRepository::save)
			.collectList()
			.then(Mono.just(data));
	}

	private TacticalCharacter processBleeding(TacticalCharacter tc) {
		if (tc.getCombatStatus().getTotalBleeding() > 0) {
			tc.getHp().add(-tc.getCombatStatus().getTotalBleeding());
		}
		return tc;
	}

	private TacticalCharacter processBuffs(TacticalCharacter tc) {
		for (Entry<Buff, Integer> buff : tc.getCombatStatus().getBuffs().entrySet()) {
			if (buff.getValue() > 0) {
				buff.setValue(buff.getValue() - 1);
			}
		}
		tc.getCombatStatus().getBuffs().entrySet().removeIf(e -> e.getValue() < 1);
		return tc;
	}

	private TacticalCharacter processDebuffs(TacticalCharacter tc) {
		for (Entry<Debuff, Integer> debuff : tc.getCombatStatus().getDebuffs().entrySet()) {
			int value = debuff.getValue();
			if (debuff.getValue() > 0) {
				debuff.setValue(value - 1);
			}
			if (value == 0 && (debuff.getKey() == Debuff.INSTANT_DEATH || debuff.getKey() == Debuff.MORTAL_DAMAGE)) {
				tc.setState(TacticalCharacterState.DEAD);
			}
		}
		tc.getCombatStatus().getDebuffs().entrySet().removeIf(e -> e.getValue() < 1);
		return tc;
	}

	private TacticalCharacter processBonus(TacticalCharacter tc) {
		for (AttackBonus bonus : tc.getCombatStatus().getBonus()) {
			if (bonus.getRounds() != null && bonus.getRounds() > 0) {
				bonus.setRounds(bonus.getRounds() - 1);
			}
		}
		tc.getCombatStatus().getBonus().removeIf(e -> e.getBonus() != null && e.getRounds() < 1);
		return tc;
	}

	private TacticalCharacter processPenalty(TacticalCharacter tc) {
		for (Penalty p : tc.getCombatStatus().getPenalties()) {
			if (p.getRounds() != null && p.getRounds() > 0) {
				p.setRounds(p.getRounds() - 1);
			}
		}
		tc.getCombatStatus().getPenalties().removeIf(e -> e.getRounds() != null && e.getRounds() < 1);
		return tc;
	}

	private TacticalCharacter processDeath(TacticalCharacter tc) {
		if (tc.getHp().getCurrent() < 1 && tc.getState() == TacticalCharacterState.NORMAL) {
			tc.setState(TacticalCharacterState.MASSIVE_SHOCK);
			//TODO comprobar numero de puntos de la constitucion para ver si entra en massive-shock-dying
		}
		return tc;
	}

}
