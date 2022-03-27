package org.labcabrera.rolemaster.core.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class AbstractControllerTest {

	@InjectMocks
	private TestController controller;

	@Mock
	private ReactiveMongoRepository<String, String> repository;

	@Mock
	private Sort sort;

	@Test
	void test() {
		when(repository.count()).thenReturn(Mono.just(100L));
		when(repository.findAll(Mockito.any(Sort.class))).thenReturn(Flux.just("1", "2", "3", "4", "5"));

		Pageable pageable = PageRequest.of(0, 3, Sort.by("foo"));

		Mono<Page<String>> page = controller.findAllUsersPaged(pageable);

		Page<String> check = page.share().block();

		assertEquals(100L, check.getTotalElements());
		assertEquals(Arrays.asList("1", "2", "3"), check.getContent());
	}

	static class TestController extends AbstractReadController<String> {

	}

}
