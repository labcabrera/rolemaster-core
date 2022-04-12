package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile;

import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissileAttackContext {

	private TacticalActionMissileAttack action;

	private MissileAttackExecution execution;

	private TacticalCharacter source;

	private TacticalCharacter target;

	public MissileAttackContext setSource(TacticalCharacter value) {
		this.source = value;
		return this;
	}

	public MissileAttackContext setTarget(TacticalCharacter value) {
		this.target = value;
		return this;
	}
}
