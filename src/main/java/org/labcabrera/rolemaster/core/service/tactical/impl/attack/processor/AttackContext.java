package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.model.item.Weapon;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class AttackContext<A extends TacticalActionAttack, E extends TacticalActionExecution> {

	@Setter
	private A action;

	@Setter
	private E execution;

	private TacticalCharacter source;

	private TacticalCharacter target;

	@Setter
	private Weapon weapon;

	@SuppressWarnings({ "unchecked", "hiding" })
	public <E> E setSource(TacticalCharacter value) {
		this.source = value;
		return (E) this;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <E> E setTarget(TacticalCharacter value) {
		this.target = value;
		return (E) this;
	}
}
