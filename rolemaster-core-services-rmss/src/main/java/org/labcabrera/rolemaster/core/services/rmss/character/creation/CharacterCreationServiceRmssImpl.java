package org.labcabrera.rolemaster.core.services.rmss.character.creation;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.dto.character.CharacterCreation;
import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.spell.Realm;
import org.labcabrera.rolemaster.core.services.character.CharacterInfoService;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.labcabrera.rolemaster.core.services.character.creation.CharacterCreationService;
import org.labcabrera.rolemaster.core.services.commons.Messages.Errors;
import org.labcabrera.rolemaster.core.services.commons.context.CharacterModificationContextLoader;
import org.labcabrera.rolemaster.core.services.commons.converter.CharacterCreationToCharacterInfoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
class CharacterCreationServiceRmssImpl implements CharacterCreationService {

	private static final String MSG_INVALID_WEAPON_SIZE = "Invalid request weapon category order count. Expected: %s, received: %s";

	@Autowired
	private CharacterInfoService characterInfoService;

	@Autowired
	private List<CharacterUpdatePostProcessor> postProcessors;

	@Autowired
	private CharacterCreationToCharacterInfoConverter converter;

	@Autowired
	private CharacterCreationItemLoaderProcessor characterCreationItemLoader;

	@Autowired
	private CharacterCreationSkillCategoryProcessor characterCreationSkillCategoryProcessor;

	@Autowired
	private CharacterCreationSkillProcessor characterCreationSkillProcessor;

	@Autowired
	private CharacterModificationContextLoader contextLoader;

	@Override
	public Mono<CharacterInfo> create(JwtAuthenticationToken auth, CharacterCreation request) {
		log.info("Processing new character {}", request.getName());
		CharacterInfo character = converter.convert(request);
		character.setVersion(RolemasterVersion.RMSS);
		return contextLoader.apply(character)
			.map(this::checkRealm)
			.map(characterCreationSkillCategoryProcessor::loadSkillCategories)
			.map(ctx -> loadSkillCategoryWeapons(ctx, request))
			.map(characterCreationSkillProcessor::loadSkills)
			.map(this::applyPostProcessors)
			.map(CharacterModificationContext::getCharacter)
			.flatMap(c -> this.characterInfoService.insert(auth, character))
			.flatMap(characterCreationItemLoader::addItems);
	}

	private CharacterModificationContext loadSkillCategoryWeapons(CharacterModificationContext context, CharacterCreation request) {
		CharacterInfo character = context.getCharacter();
		Profession profession = context.getProfession();
		int sizeExpected = profession.getSkillCategoryWeaponDevelopmentCost().size();
		int sizeReceived = request.getWeaponCategoryPriority().size();
		if (sizeExpected != sizeReceived) {
			throw new BadRequestException(String.format(MSG_INVALID_WEAPON_SIZE, sizeExpected, sizeReceived));
		}
		for (int i = 0; i < request.getWeaponCategoryPriority().size(); i++) {
			String categoryId = request.getWeaponCategoryPriority().get(i);
			List<Integer> devCost = profession.getSkillCategoryWeaponDevelopmentCost().get(i);
			CharacterSkillCategory category = character.getSkillCategories().stream()
				.filter(e -> e.getCategoryId().equals(categoryId))
				.findFirst().orElseThrow(() -> new BadRequestException(Errors.invalidSkillCategory(categoryId)));
			category.setDevelopmentCost(devCost);
		}
		return context;
	}

	private CharacterModificationContext checkRealm(CharacterModificationContext context) {
		Realm realm = context.getCharacter().getRealm();
		List<Realm> availableRealms = context.getProfession().getAvailableRealms();
		if (!availableRealms.contains(realm)) {
			throw new BadRequestException(Errors.invalidRealm(realm));
		}
		return context;
	}

	private CharacterModificationContext applyPostProcessors(CharacterModificationContext context) {
		this.postProcessors.stream().forEach(processor -> processor.accept(context));
		return context;
	}

	@Override
	public List<RolemasterVersion> compatibleVersions() {
		return Arrays.asList(RolemasterVersion.RMSS);
	}

}
