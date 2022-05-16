package org.labcabrera.rolemaster.core.dto.action.execution;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttackCriticalExecution {

	private Map<String, Integer> rolls;

}
