package org.labcabrera.rolemaster.core.service.character;

import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.labcabrera.rolemaster.core.service.character.processor.CharacterPostProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CharacterAddSkillService {

	private static final String CUSTOMIZATION_SEPARATOR = ":";

	@Autowired
	private CharacterInfoRepository characterRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private CharacterPostProcessorService postProcessor;

	public Mono<CharacterInfo> addSkill(@NotEmpty String characterId, @Valid AddSkill request) {
		return characterRepository.findById(characterId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Character " + characterId + " not found")))
			.flatMap(character -> addSkill(character, request));
	}

	public Mono<CharacterInfo> addSkill(CharacterInfo characterInfo, @Valid AddSkill request) {
		return Mono.just(characterInfo)
			.zipWith(skillRepository.findById(request.getSkillId()))
			.map(pair -> {
				CharacterInfo character = pair.getT1();
				Skill skill = pair.getT2();
				String skillId = getSkillId(skill.getId(), request.getCustomizations());
				if (character.getSkill(skillId).isPresent()) {
					throw new BadRequestException("Duplicate skill " + skillId + ".");
				}
				CharacterSkillCategory skillCategory = character.getSkillCategory(skill.getCategoryId()).get();
				CharacterSkill characterSkill = CharacterSkill.builder()
					.skillId(skillId)
					.categoryId(skill.getCategoryId())
					.group(skillCategory.getGroup())
					.progressionType(skill.getProgressionType())
					.developmentCost(skillCategory.getDevelopmentCost())
					.attributes(skillCategory.getAttributes())
					.build();
				characterSkill.getRanks().put(RankType.ADOLESCENCE, 0);
				characterSkill.getRanks().put(RankType.CONSOLIDATED, 0);
				characterSkill.getRanks().put(RankType.DEVELOPMENT, 0);
				if (skill.getCustomizableOptions() > 0) {
					if (request.getCustomizations().size() != skill.getCustomizableOptions()) {
						throw new BadRequestException("Invalid customization option count");
					}
					//TODO validate customization
					characterSkill.setCustomization(request.getCustomizations());
				}
				character.getSkills().add(characterSkill);
				return character;
			})
			.map(postProcessor::apply)
			.flatMap(characterRepository::save);
	}

	private String getSkillId(String skillId, List<String> customizations) {
		if (customizations == null || customizations.isEmpty()) {
			return skillId;
		}
		StringBuilder sb = new StringBuilder(skillId).append(CUSTOMIZATION_SEPARATOR);
		for (Iterator<String> iterator = customizations.iterator(); iterator.hasNext();) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(CUSTOMIZATION_SEPARATOR);
			}
		}
		return sb.toString();
	}

}
