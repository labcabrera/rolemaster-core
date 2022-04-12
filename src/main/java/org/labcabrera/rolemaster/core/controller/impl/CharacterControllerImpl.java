package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.CharacterController;
import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.dto.SkillUpgrade;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.model.character.inventory.CharacterInventory;
import org.labcabrera.rolemaster.core.repository.CharacterInventoryRepository;
import org.labcabrera.rolemaster.core.service.character.CharacterAddSkillService;
import org.labcabrera.rolemaster.core.service.character.CharacterService;
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
	private CharacterInventoryRepository inventoryRepository;

	@Autowired
	private CharacterAddSkillService addSkillService;

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
	public Mono<CharacterInfo> updateRanks(String characterId, SkillUpgrade request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<CharacterInfo> addSkill(String characterId, AddSkill request) {
		return addSkillService.addSkill(characterId, request);
	}

}
