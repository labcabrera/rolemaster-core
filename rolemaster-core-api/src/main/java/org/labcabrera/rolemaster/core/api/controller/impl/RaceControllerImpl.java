package org.labcabrera.rolemaster.core.api.controller.impl;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.rolemaster.core.api.controller.RaceController;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RaceControllerImpl implements RaceController {

	@Autowired
	private RaceRepository repository;

	public Mono<Race> findById(@PathVariable String id) {
		return repository.findById(id);
	}

	public Flux<Race> findAll(RolemasterVersion version, String universeId, Pageable pageable) {
		Race probe = Race.builder()
			.keywords(null)
			.attributeModifiers(null)
			.bodyDevelopmentProgression(null)
			.powerPointsProgression(null)
			.resistanceBonus(null)
			.adolescenceSkillCategoryRanks(null)
			.adolescenceSkillRanks(null)
			.skillCategoryBonus(null)
			.skillBonus(null)
			.startingLanguageSelection(null)
			.professionRestrictions(null)
			.exhaustionPointsBonus(null)
			.size(null)
			.skinAT(null)
			.version(version)
			.universes(null)
			.build();
		if (StringUtils.isNotBlank(universeId)) {
			probe.setUniverses(Arrays.asList(universeId));
		}
		Example<Race> example = Example.of(probe);
		return repository.findAll(example, pageable.getSort());
	}
}
