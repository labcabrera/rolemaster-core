package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import java.util.List;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
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

	private List<TacticalAction> actions;

	private Integer hp;

	private CriticalSeverity criticalSeverity;

	private CriticalType criticalType;

}