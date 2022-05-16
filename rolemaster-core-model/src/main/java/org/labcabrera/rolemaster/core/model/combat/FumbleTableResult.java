package org.labcabrera.rolemaster.core.model.combat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.Debuff;

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
	private Map<Debuff, Integer> debufMap = new LinkedHashMap<>();

	//TODO recibir 1d5 dmg

	public FumbleTableResult addDebuf(Debuff debufStatus, Integer rounds) {
		debufMap.put(debufStatus, rounds);
		return this;
	}
}
