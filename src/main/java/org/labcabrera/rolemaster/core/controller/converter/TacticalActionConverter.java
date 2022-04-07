package org.labcabrera.rolemaster.core.controller.converter;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.dto.actions.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.actions.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.actions.TacticalActionMovementDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleAttack;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMovement;
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
			result = TacticalActionMeleAttack.builder()
				.target(tmp.getTarget())
				.meleAttackType(tmp.getMeleeAttackType())
				.parry(tmp.getParry())
				.build();
		}
		else {
			throw new NotImplementedException("Not implemented type " + source.getClass().getSimpleName());
		}
		result.setActionPercent(source.getActionPercent());
		result.setNotes(source.getNotes());
		result.setSource(source.getSource());
		result.setPriority(source.getPriority());
		return result;
	}

}
