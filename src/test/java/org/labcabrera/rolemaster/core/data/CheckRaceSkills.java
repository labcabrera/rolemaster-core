package org.labcabrera.rolemaster.core.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.model.skill.SkillCategory;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class CheckRaceSkills {

	@Autowired
	private RaceRepository raceRepository;

	@Autowired
	private SkillCategoryRepository skillCategoryRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Test
	void test() {
		List<Race> races = raceRepository.findAll().collectList().share().block();
		List<SkillCategory> skillCategories = skillCategoryRepository.findAll().collectList().share().block();
		List<Skill> skills = skillRepository.findAll().collectList().share().block();
		List<String> errors = new ArrayList<>();

		races.stream().filter(e -> e.getAdolescenceSkillCategoryRanks() != null)
			.forEach(race -> checkSkillCategories(
				race.getId(),
				race.getAdolescenceSkillCategoryRanks().keySet(),
				skillCategories,
				errors));
		races.stream().filter(e -> e.getSkillCategoryBonus() != null)
			.forEach(race -> checkSkillCategories(
				race.getId(),
				race.getSkillCategoryBonus().keySet(),
				skillCategories,
				errors));
		races.stream().filter(e -> e.getAdolescenceSkillRanks() != null)
			.forEach(race -> checkSkills(
				race.getId(),
				race.getAdolescenceSkillRanks().keySet(),
				skills,
				errors));
		races.stream().filter(e -> e.getSkillBonus() != null)
			.forEach(race -> checkSkills(
				race.getId(),
				race.getSkillBonus().keySet(),
				skills,
				errors));
		errors.stream().forEach(e -> log.error(e));
		assertTrue(errors.isEmpty());
	}

	private void checkSkillCategories(String raceId, Set<String> categoryIds, List<SkillCategory> skillCategories, List<String> errors) {
		categoryIds.stream().forEach(e -> checkSkillCategory(raceId, e, skillCategories, errors));
	}

	private void checkSkills(String raceId, Set<String> skillIds, List<Skill> skills, List<String> errors) {
		skillIds.stream().forEach(e -> checkSkill(raceId, e, skills, errors));
	}

	private void checkSkillCategory(String raceId, String categoryId, List<SkillCategory> skillCategories, List<String> errors) {
		if (skillCategories.stream().filter(e -> e.getId().equals(categoryId)).count() != 1) {
			errors.add(String.format("Invalid skill category in race %s: '%s'", raceId, categoryId));
		}
	}

	private void checkSkill(String raceId, String skillId, List<Skill> skills, List<String> errors) {
		int index = skillId.indexOf(":");
		String effectiveSkill = index < 0 ? skillId : skillId.substring(0, index);
		if (skills.stream().filter(e -> e.getId().equals(effectiveSkill)).count() != 1) {
			errors.add(String.format("Invalid skill in race %s: '%s'", raceId, skillId));
		}
	}

}
