package org.labcabrera.rolemaster.core.controller.impl;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Mono;

public abstract class AbstractReadController<E> {

	protected ReactiveMongoRepository<E, String> repository;

	protected Mono<ResponseEntity<E>> findById(String id) {
		return repository.findById(id)
			.map(ResponseEntity::ok)
			.defaultIfEmpty(ResponseEntity.status(HttpStatus.OK).body(null));
	}

	public Mono<Page<E>> findAllUsersPaged(Pageable pageable) {
		return repository
			.count()
			.flatMap(totalItems -> {
				return repository.findAll(pageable.getSort())
					.buffer(pageable.getPageSize(), (pageable.getPageNumber() + 1))
					.elementAt(pageable.getPageNumber(), new ArrayList<>())
					.map(results -> new PageImpl<E>(results, pageable, totalItems));
			});
	}

}
