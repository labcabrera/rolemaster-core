package org.labcabrera.rolemaster.core.model.combat;

import org.labcabrera.rolemaster.core.model.character.status.CharacterStatusModifier;

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

	private CritSeverity severity;

	private Integer hp;

	private Integer stunnedRounds;

	private Integer unconsciousRounds;

	private Integer canNotParryRounds;

	private Integer mustParryRounds;

	private Integer mustParryPenalty;

	private Bleeding bleeding;

	private Penalty penalty;

	private Integer bonus;

	private String text;

}
