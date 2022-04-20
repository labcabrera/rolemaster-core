package org.labcabrera.rolemaster.core.dto.action.execution;

import java.util.EnumMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackFacing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MeleeAttackExecution extends TacticalActionExecution {

	@Builder.Default
	@Schema(description = "Secondary roll when using two weapons.")
	private Map<AttackTargetType, OpenRoll> rolls = new EnumMap<>(AttackTargetType.class);

	@Builder.Default
	private Map<AttackTargetType, String> targets = new EnumMap<>(AttackTargetType.class);

	@Builder.Default
	private Map<AttackTargetType, MeleeAttackFacing> facingMap = new EnumMap<>(AttackTargetType.class);

}
