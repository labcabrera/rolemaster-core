package org.labcabrera.rolemaster.core.service.character;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.SkillCategory;
import org.labcabrera.rolemaster.core.repository.ProfessionRepository;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@SpringBootTest
@Slf4j
class CharacterReactiveTest {

	@Autowired
	private RaceRepository raceRepository;

	@Autowired
	private ProfessionRepository professionRepository;

	@Autowired
	private SkillCategoryRepository skillCategoryRepository;

	@Test
	void testFlatMany01() {
		Mono<String> mono1 = Mono.just("x");
		Flux<String> flux1 = Flux.just("{1}", "{2}", "{3}", "{4}");
		mono1
			.flatMapMany(m -> flux1.map(x -> Tuples.of(x, m)))
			.subscribe(System.out::println);
	}

	@Test
	void testFlatMany02() {
		Mono<String> mono = Mono.just("character");
		Flux<String> flux = Flux.just("skill1", "skill2", "skill3", "skill4");
		mono
			.flatMapMany(m -> flux.map(x -> m + "-" + x))
			.subscribe(System.out::println);
	}

	@Test
	void test() {
		CharInfo c = CharInfo.builder()
			.raceId("common-men")
			.professionId("figther")
			.build();
		TestContext context = TestContext.builder().characterInfo(c).build();

		Mono<Race> monoRace = raceRepository.findById(c.getRaceId());
		Mono<Profession> monoProfession = professionRepository.findById(c.getProfessionId());
		Flux<SkillCategory> fluxSkillCategories = skillCategoryRepository.findAll();

		Mono<TestContext> map = Mono.just(context)
			.zipWith(monoRace)
			.map(tuple -> {
				tuple.getT1().setRace(tuple.getT2());
				return tuple.getT1();
			})
			.zipWith(monoProfession)
			.map(tuple -> {
				tuple.getT1().setProfession(tuple.getT2());
				return tuple.getT1();
			})
		//			.flatMapMany(c -> {
		//				return c;
		//			});
		;

		map.doOnNext(e -> log.debug("Context: {}", e))
			.subscribe();
	}

	@Data
	@Builder
	static class CharInfo {
		private String raceId;
		private String professionId;
		private List<String> skillCategories;
	}

	@Data
	@Builder
	static class TestContext {
		private CharInfo characterInfo;
		private Race race;
		private Profession profession;
	}

}
