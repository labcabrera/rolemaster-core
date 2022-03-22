package org.labcabrera.rolemaster.core.model.character.creation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.Skill;
import org.labcabrera.rolemaster.core.model.character.SkillCategory;

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

	@Builder.Default
	private List<SkillCategory> skillCategories = new ArrayList<>();

	@Builder.Default
	private List<Skill> skills = new ArrayList<>();

}
