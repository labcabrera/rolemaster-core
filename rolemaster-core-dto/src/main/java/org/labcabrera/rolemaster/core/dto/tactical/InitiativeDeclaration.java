package org.labcabrera.rolemaster.core.dto.tactical;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitiativeDeclaration {

	@Valid
	@NotEmpty
	@Builder.Default
	private List<TacticalCharacterInitiativeDeclaration> characters = new ArrayList<>();

	public Optional<TacticalCharacterInitiativeDeclaration> findByCharacterId(String characterId) {
		return characters.stream().filter(e -> e.getCharacterId().equals(characterId)).findFirst();
	}
}
