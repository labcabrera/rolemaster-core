package org.labcabrera.rolemaster.core.service.character.adapter;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.Skill;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Order(100)
@Slf4j
public class CharacterSkillAdapter implements CharacterAdapter {

	@Autowired
	private SkillRepository skillRepository;

	@Override
	public Mono<CharacterModificationContext> apply(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		log.debug("Loading skills for {}", character.getId() == null ? "new character" : "character " + character.getId());
		Flux.fromIterable(character.getSkills())
			.doOnNext(e -> log.debug("Processing skill {}", e))
			.flatMap(e -> loadSkill(character, e))
			.map(e -> character)
			.subscribe();
		return Mono.just(context);

	}

	private Mono<CharacterInfo> loadSkill(CharacterInfo character, CharacterSkill characterSkill) {
		return skillRepository
			.findById(characterSkill.getSkillId())
			.map(skill -> {
				loadAttributeBonus(character, characterSkill, skill);
				return characterSkill;
			})
			.doOnNext(e -> log.debug("Loaded skill {}", e))
			.map(e -> character);
	}

	private void loadAttributeBonus(CharacterInfo character, CharacterSkill characterSkill, Skill skill) {
		int bonus = 0;
		if (skill.getAttributeBonus() != null) {
			for (AttributeType i : skill.getAttributeBonus()) {
				bonus += character.getAttributes().get(i).getTotalBonus();
			}
		}
		else {
			log.debug("Missing skill {} attribute bonus", skill.getId());
		}
		characterSkill.setAttributeBonus(bonus);
	}

}
