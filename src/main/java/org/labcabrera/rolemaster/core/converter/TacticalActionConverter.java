package org.labcabrera.rolemaster.core.converter;

import java.util.Collections;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMissileAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMovementDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMovingManeuverDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionStaticManeuverDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovement;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovingManeuver;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionStaticManeuver;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TacticalActionConverter implements Converter<TacticalActionDeclaration, TacticalAction> {

	@Override
	public TacticalAction convert(TacticalActionDeclaration source) {
		TacticalAction result = null;
		if (source instanceof TacticalActionMovementDeclaration tmp) {
			result = TacticalActionMovement.builder()
				.pace(tmp.getPace())
				.build();
		}
		else if (source instanceof TacticalActionMeleeAttackDeclaration tmp) {
			result = TacticalActionMeleeAttack.builder()
				.targets(tmp.getTargets())
				.meleeAttackMode(tmp.getMeleeAttackMode())
				.meleeAttackType(tmp.getMeleeAttackType())
				.parry(tmp.getParry())
				.build();
		}
		else if (source instanceof TacticalActionMissileAttackDeclaration tmp) {
			result = TacticalActionMissileAttack.builder()
				.targets(Collections.singletonMap(AttackTargetType.MAIN_HAND, tmp.getTarget()))
				.build();
		}
		else if (source instanceof TacticalActionMovingManeuverDeclaration tmp) {
			result = TacticalActionMovingManeuver.builder()
				.skillId(tmp.getSkillId())
				.build();
		}
		else if (source instanceof TacticalActionStaticManeuverDeclaration tmp) {
			result = TacticalActionStaticManeuver.builder()
				.skillId(tmp.getSkillId())
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
