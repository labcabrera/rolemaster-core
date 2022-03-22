package org.labcabrera.rolemaster.core.model.character.creation;

import javax.validation.constraints.NotEmpty;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@NotEmpty
@Builder
public class CharacterModificationContextImpl implements CharacterModificationContext {

	private CharacterInfo character;

	private Race race;

	private Profession profession;

}
