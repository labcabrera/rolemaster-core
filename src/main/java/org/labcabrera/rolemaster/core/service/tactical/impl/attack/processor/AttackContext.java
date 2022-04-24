package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.EnumMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.item.Weapon;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class AttackContext {

	@Setter
	private TacticalActionAttack action;

	private TacticalCharacter source;

	private Map<AttackTargetType, TacticalCharacter> targets = new EnumMap<>(AttackTargetType.class);

	@Setter
	private Weapon weapon;

	public AttackContext(TacticalActionAttack action) {
		this.action = action;
	}

	public AttackContext setSource(TacticalCharacter value) {
		this.source = value;
		return this;
	}

	public boolean isMeleeAttack() {
		return action instanceof TacticalActionMeleeAttack;
	}

}
