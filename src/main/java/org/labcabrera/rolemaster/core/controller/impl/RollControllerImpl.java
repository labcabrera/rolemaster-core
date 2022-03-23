package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.RollController;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class RollControllerImpl implements RollController {

	@Autowired
	private RandomService randomService;

	@Override
	public Mono<Integer> randomRoll(Integer max) {
		return Mono.just(randomService.dX(max));
	}

	@Override
	public Mono<OpenRoll> randomOpenRoll() {
		return Mono.just(randomService.d100FullOpen());
	}

}
