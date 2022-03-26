package org.labcabrera.rolemaster.core.model.character.creation;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.model.skill.SkillCategory;

public interface CharacterModificationContext {

	CharacterInfo getCharacter();

	Race getRace();

	void setRace(Race race);

	Profession getProfession();

	void setProfession(Profession profession);

	List<SkillCategory> getSkillCategories();

	void setSkillCategories(List<SkillCategory> list);

	List<Skill> getSkills();

	void setSkills(List<Skill> list);

}
