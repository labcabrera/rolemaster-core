package org.labcabrera.rolemaster.core.services.commons.context;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.repository.ProfessionRepository;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.labcabrera.rolemaster.core.services.commons.Messages.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class CharacterModificationContextLoader {

	@Autowired
	private RaceRepository raceRepository;

	@Autowired
	private ProfessionRepository professionRepository;

	@Autowired
	private SkillCategoryRepository skillCategoryRepository;

	@Autowired
	private SkillRepository skillRepository;

	public Mono<CharacterModificationContext> apply(CharacterInfo characterInfo) {
		CharacterModificationContext context = CharacterModificationContext.builder().character(characterInfo).build();
		return Mono.just(context)
			.flatMap(this::loadRace)
			.flatMap(this::loadProfession)
			.flatMap(this::loadSkillCategories)
			.flatMap(this::loadSkills);
	}

	public Mono<CharacterModificationContext> loadRace(CharacterModificationContext context) {
		String raceId = context.getCharacter().getRaceId();
		return raceRepository.findById(raceId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException(Errors.raceNotFound(raceId))))
			.map(race -> {
				context.setRace(race);
				return context;
			});
	}

	public Mono<CharacterModificationContext> loadProfession(CharacterModificationContext context) {
		String professionId = context.getCharacter().getProfessionId();
		return professionRepository.findById(professionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException(Errors.professionNotFound(professionId))))
			.map(profession -> {
				context.setProfession(profession);
				return context;
			});
	}

	public Mono<CharacterModificationContext> loadSkillCategories(CharacterModificationContext context) {
		RolemasterVersion version = context.getCharacter().getVersion();
		return skillCategoryRepository.findByVersion(version).collectList()
			.map(list -> {
				context.setSkillCategories(list);
				return context;
			});
	}

	public Mono<CharacterModificationContext> loadSkills(CharacterModificationContext context) {
		RolemasterVersion version = context.getCharacter().getVersion();
		return skillRepository.findByVersion(version).collectList()
			.map(list -> {
				context.setSkills(list);
				return context;
			});
	}

}
