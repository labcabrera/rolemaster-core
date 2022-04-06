package org.labcabrera.rolemaster.core.controller.graphql;

import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;

@Controller
public class RaceController {

	@Autowired
	private RaceRepository repository;

	@QueryMapping
	public Flux<Race> companies() {
		return repository.findAll();
	}

}