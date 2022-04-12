package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.item.Weapon;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttackContext {

	private TacticalActionMeleeAttack action;

	private MeleeAttackExecution execution;

	private TacticalCharacter source;

	private TacticalCharacter target;

	private Weapon weapon;

	@SuppressWarnings("unchecked")
	public <E> E setSource(TacticalCharacter value) {
		this.source = value;
		return (E) this;
	}

	@SuppressWarnings("unchecked")
	public <E> E setTarget(TacticalCharacter value) {
		this.target = value;
		return (E) this;
	}
}
