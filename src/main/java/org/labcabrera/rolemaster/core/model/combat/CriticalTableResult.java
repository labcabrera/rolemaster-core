package org.labcabrera.rolemaster.core.model.combat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.CharacterStatusModifier;
import org.labcabrera.rolemaster.core.model.tactical.DebufStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a result of a given critical table.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriticalTableResult implements CharacterStatusModifier {

	private Integer maxRoll;

	private CriticalSeverity severity;

	private Integer hp;

	@Builder.Default
	private Map<DebufStatus, Integer> debufMap = new LinkedHashMap<>();

	private Integer mustParryPenalty;

	private Integer bleeding;

	private Penalty penalty;

	private Integer bonus;

	@Builder.Default
	private Boolean specialEffect = false;

	private String text;

	private Integer deathAfterRounds;

	@Builder.Default
	private List<String> otherEfects = new ArrayList<>();

	public CriticalTableResult addDebuf(DebufStatus debufStatus) {
		return addDebuf(debufStatus, 1);
	}

	public CriticalTableResult addDebuf(DebufStatus debufStatus, Integer rounds) {
		debufMap.put(debufStatus, rounds);
		return this;
	}

	public CriticalTableResult addPenalty(Integer penalty) {
		return addPenalty(penalty, 1);
	}

	public CriticalTableResult addPenalty(Integer penalty, Integer rounds) {
		if (penalty > 0) {
			throw new RuntimeException("Invalid penalty");
		}
		this.penalty = Penalty.builder().penalty(penalty).rounds(rounds).build();
		return this;
	}

	public CriticalTableResult addEfect(String description) {
		this.otherEfects.add(description);
		return this;
	}

}
