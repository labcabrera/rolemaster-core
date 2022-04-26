package org.labcabrera.rolemaster.core.dto.action.execution;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeaponBreakageExecution {

	private Map<AttackTargetType, Integer> rolls;

}
