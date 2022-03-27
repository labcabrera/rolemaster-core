package org.labcabrera.rolemaster.core.model.combat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriticalResult {

	private Integer maxRoll;

	private CritSeverity severity;

	private Integer hp;

	private Integer stunned;

	private Integer canNotParryRounds;

	private Integer mustParryRounds;

	private Integer mustParryPenalty;

	private Integer bleed;

	private Integer bleedRounds;

	private Integer penalty;

	private Integer penaltyRounds;

	private Integer bonus;

	private String text;
}
