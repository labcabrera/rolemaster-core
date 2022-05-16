package org.labcabrera.rolemaster.core.model.character;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterEquipement {

	@Id
	private String id;

	private String characterId;

}
