package org.labcabrera.rolemaster.core.model.combat;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.CharacterStatusModifier;
import org.labcabrera.rolemaster.core.model.tactical.DebufStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriticalResult implements CharacterStatusModifier {

	private Integer maxRoll;

	private CriticalSeverity severity;

	private Integer hp;

	private Map<DebufStatus, Integer> debufMap;

	private Integer mustParryPenalty;

	private Bleeding bleeding;

	private Penalty penalty;

	private Integer bonus;

	private String text;

}
