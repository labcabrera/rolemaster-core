package org.labcabrera.rolemaster.core.service.character.creation;

import java.util.Arrays;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterCreationStatus;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContextImpl;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.ProfessionRepository;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.labcabrera.rolemaster.core.service.character.AttributeCreationService;
import org.labcabrera.rolemaster.core.service.character.adapter.CharacterExhaustionAdapter;
import org.labcabrera.rolemaster.core.service.character.adapter.CharacterHpAdapter;
import org.labcabrera.rolemaster.core.service.character.adapter.CharacterSkillAdapter;
import org.labcabrera.rolemaster.core.table.attribute.AttributeBonusTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CharacterCreationService {

	@Autowired
	private AttributeCreationService attributeCreationService;

	@Autowired
	private AttributeBonusTable attributeBonusTable;

	@Autowired
	private CharacterInfoRepository repository;

	@Autowired
	private CharacterSkillAdapter skillAdapter;

	@Autowired
	private CharacterHpAdapter hpAdapter;

	@Autowired
	private CharacterExhaustionAdapter exhaustionAdapter;

	@Autowired
	private CharacterCreationSkillCategoryService skillCategoryService;

	@Autowired
	private CharacterCreationSkillService skillCreationService;

	@Autowired
	private RaceRepository raceRepository;

	@Autowired
	private ProfessionRepository professionRepository;

	@Autowired
	private SkillCategoryRepository skillCategoryRepository;

	@Autowired
	private SkillRepository skillRepository;

	public Mono<CharacterInfo> create(CharacterCreationRequest request) {
		log.info("Processing new character {}", request.getName());

		final CharacterInfo character = CharacterInfo.builder()
			.name(request.getName())
			.raceId(request.getRaceId())
			.professionId(request.getProfessionId())
			.creationStatus(CharacterCreationStatus.PARTIALLY_CREATED)
			.build();

		loadAttributes(character, request);

		final CharacterModificationContext context = CharacterModificationContextImpl.builder()
			.character(character)
			.build();

		return Mono.just(context)
			.zipWith(raceRepository.findById(request.getRaceId()))
			.map(tuple -> {
				tuple.getT1().setRace(tuple.getT2());
				return tuple.getT1();
			})
			.zipWith(professionRepository.findById(request.getProfessionId()))
			.map(tuple -> {
				tuple.getT1().setProfession(tuple.getT2());
				return tuple.getT1();
			})
			.flatMap(ctx -> {
				return skillCategoryRepository.findAll()
					.collectList()
					.doOnNext(list -> ctx.setSkillCategories(list))
					.map(e -> ctx);
			})
			.flatMap(ctx -> {
				return skillRepository.findSkillsOnNewCharacter()
					.collectList()
					.doOnNext(list -> ctx.setSkills(list))
					.map(e -> ctx);
			})
			.map(skillCategoryService::initialize)
			.map(skillCreationService::initialize)
			.map(skillAdapter::apply)
			.map(hpAdapter::apply)
			.map(exhaustionAdapter::apply)
			.map(ctx -> ctx.getCharacter())
			.flatMap(repository::save);

	}

	private void loadAttributes(CharacterInfo character, CharacterCreationRequest request) {
		Arrays.asList(AttributeType.values()).stream().forEach(e -> {
			int value = request.getBaseAttributes().containsKey(e) ? request.getBaseAttributes().get(e) : 1;
			character.getAttributes().put(e, CharacterAttribute.builder()
				.currentValue(value)
				.potentialValue(attributeCreationService.getPotentialStat(value))
				.baseBonus(attributeBonusTable.getBonus(value))
				.build());
		});
	}

}
