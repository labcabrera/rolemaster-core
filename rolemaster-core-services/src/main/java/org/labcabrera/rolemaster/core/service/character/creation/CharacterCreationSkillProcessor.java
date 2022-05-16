package org.labcabrera.rolemaster.core.service.character.creation;

import java.util.List;
import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.message.Messages.Errors;
import org.labcabrera.rolemaster.core.model.character.BonusType;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.springframework.stereotype.Component;

@Component
public class CharacterCreationSkillProcessor {

	public CharacterModificationContext loadSkills(CharacterModificationContext context) {
		Race race = context.getRace();
		context.getSkills().stream().forEach(skill -> {
			String categoryId = skill.getCategoryId();
			String skillId = skill.getId();
			Integer adolescenceRanks = race.getAdolescenceSkillRanks().getOrDefault(skill.getId(), 0);
			if (skill.getCustomizableOptions() > 0) {
				List<Entry<String, Integer>> list = race.getAdolescenceSkillRanks().entrySet().stream()
					.filter(e -> e.getKey().startsWith(skill.getId()))
					.toList();
				if (!list.isEmpty()) {
					skillId = list.iterator().next().getKey();
					adolescenceRanks = list.iterator().next().getValue();
				}
			}
			CharacterSkillCategory category = context.getCharacter().getSkillCategory(categoryId)
				.orElseThrow(() -> new BadRequestException(Errors.invalidSkillCategory(categoryId)));
			CharacterSkill cs = CharacterSkill.builder()
				.skillId(skillId)
				.categoryId(skill.getCategoryId())
				.group(category.getGroup())
				.developmentCost(category.getDevelopmentCost())
				.attributes(category.getAttributes())
				.progressionType(skill.getProgressionType())
				.build();
			cs.getRanks().put(RankType.ADOLESCENCE, adolescenceRanks);
			cs.getRanks().put(RankType.CONSOLIDATED, 0);
			cs.getRanks().put(RankType.DEVELOPMENT, 0);
			cs.getBonus().put(BonusType.SKILL_SPECIAL, skill.getSkillBonus());
			context.getCharacter().getSkills().add(cs);
		});
		return context;
	}
}
