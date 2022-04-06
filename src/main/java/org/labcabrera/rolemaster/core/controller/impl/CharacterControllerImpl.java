package org.labcabrera.rolemaster.core.controller.impl;

import java.util.EnumMap;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.CharacterController;
import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.dto.SkillAndTrainingPackageUpgrade;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.character.BonusType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.model.character.inventory.CharacterInventory;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.CharacterInventoryRepository;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.labcabrera.rolemaster.core.service.character.CharacterService;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@RestController
public class CharacterControllerImpl implements CharacterController {

	@Autowired
	private CharacterService characterService;

	@Autowired
	private CharacterCreationService creationService;

	@Autowired
	private CharacterInfoRepository characterInfoRepository;

	@Autowired
	private CharacterInventoryRepository inventoryRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Override
	public Mono<CharacterInfo> findById(String id) {
		return characterService.findById(id);
	}

	@Override
	public Mono<CharacterInfo> create(@Valid CharacterCreationRequest request) {
		return creationService.create(request);
	}

	@Override
	public Flux<CharacterInfo> findAll(Pageable pageable) {
		return characterService.findAll(pageable);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return characterService.deleteById(id);
	}

	@Override
	public Mono<CharacterInventory> findCharacterInventoryById(String id) {
		return inventoryRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Character inventory not found.")));
	}

	@Override
	public Mono<CharacterInfo> updateRanks(String characterId, SkillAndTrainingPackageUpgrade request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<CharacterInfo> addSkill(String characterId, AddSkill request) {
		return characterInfoRepository.findById(characterId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Character not found")))
			.zipWith(skillRepository.findById(request.getSkillId()))
			.map(pair -> {
				CharacterInfo character = pair.getT1();
				Skill skill = pair.getT2();
				if (!skill.getCustomizable() && request.getCustomization() != null) {
					throw new BadRequestException("Skill is not customizable");
				}
				String skillCategoryId = skill.getCategoryId();
				CharacterSkillCategory skillCategory = character.getSkillCategories().stream()
					.filter(e -> skillCategoryId.equals(e.getCategoryId()))
					.findFirst().orElseThrow(() -> new RuntimeException("Expected skill category " + skillCategoryId));
				//NOTE: race and profession bonus must be 0
				CharacterSkill cs = CharacterSkill.builder()
					.skillId(request.getSkillId())
					.customization(request.getCustomization())
					.group(skillCategory.getGroup())
					.progressionType(skill.getProgressionType())
					.developmentCost(skillCategory.getDevelopmentCost())
					.attributes(skillCategory.getAttributes())
					.ranks(new EnumMap<>(RankType.class))
					.bonus(new EnumMap<>(BonusType.class))
					.build();
				return Tuples.of(character, cs);
			})
			.map(pair -> {
				return pair.getT1();
			})
			.flatMap(characterInfoRepository::save);
	}

}
