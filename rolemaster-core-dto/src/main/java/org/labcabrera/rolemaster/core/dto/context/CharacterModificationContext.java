package org.labcabrera.rolemaster.core.dto.context;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.model.skill.SkillCategory;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterModificationContext {

	private Authentication auth;

	private CharacterInfo character;

	private Race race;

	private Profession profession;

	private List<SkillCategory> skillCategories;

	private List<Skill> skills;

}
