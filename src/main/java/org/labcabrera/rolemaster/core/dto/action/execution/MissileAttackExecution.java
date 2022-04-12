package org.labcabrera.rolemaster.core.dto.action.execution;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.combat.MissileCover;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MissileAttackExecution extends TacticalActionExecution {

	private OpenRoll roll;

	private MissileCover cover;

}
