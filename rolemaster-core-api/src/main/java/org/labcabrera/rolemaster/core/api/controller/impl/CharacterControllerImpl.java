package org.labcabrera.rolemaster.core.api.controller.impl;

import javax.validation.Valid;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.api.controller.CharacterController;
import org.labcabrera.rolemaster.core.dto.AddFlaw;
import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.dto.AddTalent;
import org.labcabrera.rolemaster.core.dto.SkillUpgrade;
import org.labcabrera.rolemaster.core.dto.TrainingPackageUpgrade;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreation;
import org.labcabrera.rolemaster.core.services.character.CharacterAddSkillService;
import org.labcabrera.rolemaster.core.services.character.CharacterAddTrainingPackageService;
import org.labcabrera.rolemaster.core.services.character.CharacterInfoService;
import org.labcabrera.rolemaster.core.services.character.creation.CharacterCreationService;
import org.labcabrera.rolemaster.core.services.rmss.character.CharacterUpdateSkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CharacterControllerImpl implements CharacterController {

	@Autowired
	private CharacterInfoService characterService;

	@Autowired
	private CharacterCreationService creationService;

	@Autowired
	private CharacterAddSkillService addSkillService;

	@Autowired
	private CharacterUpdateSkillServiceImpl characterUpdateSkillService;

	@Autowired
	private CharacterAddTrainingPackageService trainingPackageUpgradeService;

	@Override
	public Mono<CharacterInfo> findById(JwtAuthenticationToken auth, String id) {
		return characterService.findById(auth, id);
	}

	@Override
	public Mono<CharacterInfo> create(JwtAuthenticationToken auth, @Valid CharacterCreation request) {
		return creationService.create(auth, request);
	}

	@Override
	public Flux<CharacterInfo> findAll(JwtAuthenticationToken auth, Pageable pageable) {
		return characterService.findAll(auth, pageable);
	}

	@Override
	public Mono<Void> deleteById(JwtAuthenticationToken auth, String id) {
		return characterService.deleteById(auth, id);
	}

	@Override
	public Mono<CharacterInfo> updateRanks(JwtAuthenticationToken auth, String characterId, SkillUpgrade request) {
		return characterUpdateSkillService.updateRanks(auth, characterId, request);
	}

	@Override
	public Mono<CharacterInfo> addSkill(JwtAuthenticationToken auth, String characterId, AddSkill request) {
		return addSkillService.addSkill(auth, characterId, request);
	}

	@Override
	public Mono<CharacterInfo> addTrainingPackage(JwtAuthenticationToken auth, String characterId, TrainingPackageUpgrade request) {
		return trainingPackageUpgradeService.upgrade(auth, characterId, request);
	}

	@Override
	public Mono<CharacterInfo> addTalent(JwtAuthenticationToken auth, String characterId, AddTalent request) {
		throw new NotImplementedException("Not implemented.");
	}

	@Override
	public Mono<CharacterInfo> addFlaw(JwtAuthenticationToken auth, String characterId, AddFlaw request) {
		throw new NotImplementedException("Not implemented.");
	}

}
