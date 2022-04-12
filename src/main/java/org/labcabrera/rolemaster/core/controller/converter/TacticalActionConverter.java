package org.labcabrera.rolemaster.core.controller.converter;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMissileAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMovementDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovement;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TacticalActionConverter implements Converter<TacticalActionDeclaration, TacticalAction> {

	@Override
	public TacticalAction convert(TacticalActionDeclaration source) {
		TacticalAction result = null;
		if (source instanceof TacticalActionMovementDeclaration) {
			TacticalActionMovementDeclaration tmp = (TacticalActionMovementDeclaration) source;
			result = TacticalActionMovement.builder()
				.pace(tmp.getPace())
				.build();
		}
		else if (source instanceof TacticalActionMeleeAttackDeclaration) {
			TacticalActionMeleeAttackDeclaration tmp = (TacticalActionMeleeAttackDeclaration) source;
			result = TacticalActionMeleeAttack.builder()
				.target(tmp.getTarget())
				.meleeAttackType(tmp.getMeleeAttackType())
				.parry(tmp.getParry())
				.build();
		}
		else if (source instanceof TacticalActionMissileAttackDeclaration) {
			TacticalActionMissileAttackDeclaration tmp = (TacticalActionMissileAttackDeclaration) source;
			result = TacticalActionMissileAttack.builder()
				.target(tmp.getTarget())
				.build();
		}
		else {
			throw new NotImplementedException("Not implemented type " + source.getClass().getSimpleName());
		}
		result.setRoundId(source.getRoundId());
		result.setActionPercent(source.getActionPercent());
		result.setNotes(source.getNotes());
		result.setSource(source.getSource());
		result.setPriority(source.getPriority());
		return result;
	}

}
