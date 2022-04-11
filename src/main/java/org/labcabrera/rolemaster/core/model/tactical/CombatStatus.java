package org.labcabrera.rolemaster.core.model.tactical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.combat.Bleeding;
import org.labcabrera.rolemaster.core.model.combat.Penalty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CombatStatus {

	private Map<DebufStatus, Integer> debufStatusMap;

	@Builder.Default
	@Schema(description = "Amount of round activity required to complete the missile weapon reload.")
	private Integer reloadingActivityPercent = 0;

	@Builder.Default
	private List<Bleeding> bleding = new ArrayList<>();

	@Builder.Default
	private List<Penalty> penalty = new ArrayList<>();

}
