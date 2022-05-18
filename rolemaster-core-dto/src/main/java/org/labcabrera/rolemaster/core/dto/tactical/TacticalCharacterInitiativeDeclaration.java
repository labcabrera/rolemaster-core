package org.labcabrera.rolemaster.core.dto.tactical;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalCharacterInitiativeDeclaration {

	@NotEmpty
	private String characterId;

	@Builder.Default
	private Map<String, Integer> customModifiers = new LinkedHashMap<>();

	@NotNull
	private Integer initiativeRoll;

}
