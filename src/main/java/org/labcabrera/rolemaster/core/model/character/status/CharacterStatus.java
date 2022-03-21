package org.labcabrera.rolemaster.core.model.character.status;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterStatus {

	@Id
	private String id;

	private String sessionId;

	private String characterId;

	private Integer hp;

	@Builder.Default
	private Integer stunned = 0;

	@Builder.Default
	private Integer unconscious = 0;

}
