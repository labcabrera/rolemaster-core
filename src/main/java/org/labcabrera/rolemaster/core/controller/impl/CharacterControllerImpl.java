package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.CharacterController;
import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.dto.SkillUpgrade;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.service.character.CharacterAddSkillService;
import org.labcabrera.rolemaster.core.service.character.CharacterService;
import org.labcabrera.rolemaster.core.service.character.CharacterUpdateSkillService;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CharacterControllerImpl implements CharacterController {

	@Autowired
	private CharacterService characterService;

	@Autowired
	private CharacterCreationService creationService;

	@Autowired
	private CharacterAddSkillService addSkillService;

	@Autowired
	private CharacterUpdateSkillService characterUpdateSkillService;

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
	public Mono<CharacterInfo> updateRanks(String characterId, SkillUpgrade request) {
		return characterUpdateSkillService.updateRanks(characterId, request);
	}

	@Override
	public Mono<CharacterInfo> addSkill(String characterId, AddSkill request) {
		return addSkillService.addSkill(characterId, request);
	}

}
