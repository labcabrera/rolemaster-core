package org.labcabrera.rolemaster.core.model.combat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.DebufStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FumbleTableResult {

	private String text;

	private Boolean specialResult;

	@Builder.Default
	private Map<DebufStatus, Integer> debufMap = new LinkedHashMap<>();

	//TODO recibir 1d5 dmg

	public FumbleTableResult addDebuf(DebufStatus debufStatus, Integer rounds) {
		debufMap.put(debufStatus, rounds);
		return this;
	}
}
