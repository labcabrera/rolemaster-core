package org.labcabrera.rolemaster.core.reactor;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;

class ReactorTest {

	@Test
	void testFlatMany01() {
		Mono<String> mono1 = Mono.just("x");
		Flux<String> flux1 = Flux.just("{1}", "{2}", "{3}", "{4}");
		mono1
			.flatMapMany(m -> flux1.map(x -> Tuples.of(x, m)))
			.subscribe(System.out::println);
	}

	@Test
	void test03() {
		Mono<TestContext> mono = Mono.just(TestContext.builder().name("ctx-1").build());
		Flux<String> flux = Flux.just("skill1", "skill2", "skill3", "skill4");
	}

	@Test
	public void thenManyThenMany() {
		StepVerifier.create(Flux.just(1, 2, 3)
			.thenMany(Flux.just("test", "test2"))
			.thenMany(Flux.just(1L, 2L)))
			.expectNext(1L, 2L)
			.verifyComplete();
	}

	@Test
	void testFlatMany02() {
		Mono<TestContext> mono = Mono.just(TestContext.builder().name("ctx-1").build());
		Flux<String> flux = Flux.just("skill1", "skill2", "skill3", "skill4");
		Flux<TestContext> check = mono.flatMapMany(m -> flux.map(e -> {
			m.getValues().add(e);
			return m;
		}));
		check.subscribe(System.out::println);
	}

	@Test
	void processOrFallback() {
		Mono<String> source = Mono.empty();
		Publisher<String> fallback = Mono.just("uno dos tres\nfoo bar");
		Flux<String> flux = source
			.flatMapMany(phrase -> Flux.fromArray(phrase.split("\\s+")))
			.switchIfEmpty(fallback);
		flux.subscribe(System.out::println);
	}

	@Data
	@Builder
	static class TestContext {
		private String name;
		@Builder.Default
		private List<String> values = new ArrayList<>();
	}

}
