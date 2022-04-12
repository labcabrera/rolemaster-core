package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleeAttack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeleeAttackContext {

	private TacticalActionMeleeAttack action;

	private MeleeAttackExecution execution;

	private TacticalCharacter source;

	private TacticalCharacter target;

	public MeleeAttackContext setSource(TacticalCharacter value) {
		this.source = value;
		return this;
	}

	public MeleeAttackContext setTarget(TacticalCharacter value) {
		this.target = value;
		return this;
	}

}