package org.labcabrera.rolemaster.core.reactor;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;

import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

class CharacterReactiveTest {

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
