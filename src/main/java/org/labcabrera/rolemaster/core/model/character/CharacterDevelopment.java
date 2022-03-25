package org.labcabrera.rolemaster.core.model.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterDevelopment {

	private Integer totalPoints;

	@Builder.Default
	private Integer remainingPoints = 0;

}
