package org.labcabrera.rolemaster.core.service.character.creation;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.SkillCategory;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CharacterCreationSkillCategoryService {

	@Autowired
	private SkillCategoryRepository repository;

	@Autowired
	private RaceRepository raceRepository;

	public Mono<CharacterInfo> initialize(CharacterInfo character) {
		repository.findAll()
			.map(category -> addCharacterSkillCategory(character, category))
			.doOnNext(e -> log.debug("Added skill category {}", e))
			.flatMap(category -> {
				return raceRepository.findById(character.getRaceId())
					.map(race -> loadRaceSkillCategories(character, race));
			})
			.subscribe();
		return Mono.just(character);
	}

	private CharacterInfo addCharacterSkillCategory(CharacterInfo character, SkillCategory category) {
		CharacterSkillCategory characterSkillCategory = CharacterSkillCategory.builder()
			.categoryId(category.getId())
			.build();
		character.getSkillCategories().add(characterSkillCategory);
		return character;
	}

	private CharacterInfo loadRaceSkillCategories(CharacterInfo character, Race race) {
		log.debug("Loading race {} skill categories", race.getId());
		race.getAdolescenseSkillCategoryRanks().keySet().stream().forEach(categoryId -> {
			Integer rank = race.getAdolescenseSkillCategoryRanks().get(categoryId);
			character.getSkillCategories().stream()
				.filter(e -> e.getCategoryId().equals(categoryId))
				.findFirst().orElseThrow()
				.setAdolescenseRank(rank);
		});
		return character;
	}

}
