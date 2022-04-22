package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.EnumMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.item.Weapon;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class AttackContext<A extends TacticalActionAttack> {

	@Setter
	private A action;

	private TacticalCharacter source;

	private Map<AttackTargetType, TacticalCharacter> targets = new EnumMap<>(AttackTargetType.class);

	@Setter
	private Weapon weapon;

	@SuppressWarnings({ "unchecked" })
	public <E> E setSource(TacticalCharacter value) {
		this.source = value;
		return (E) this;
	}

	public boolean isMeleeAttack() {
		return action instanceof TacticalActionMeleeAttack;
	}

}
