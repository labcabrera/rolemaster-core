package org.labcabrera.rolemaster.core.dto.tactical;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.tactical.action.InitiativeModifier;

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
	private Map<InitiativeModifier, Integer> customModifiers = new LinkedHashMap<>();

	@NotNull
	private Integer initiativeRoll;

}
