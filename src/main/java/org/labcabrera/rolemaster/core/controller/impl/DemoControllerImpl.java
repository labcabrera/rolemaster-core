package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.DemoController;
import org.labcabrera.rolemaster.core.service.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class DemoControllerImpl implements DemoController {

	@Autowired
	private DemoService demoService;

	@Override
	public Mono<Void> initialize() {
		log.info("Processing demo initialization");
		return demoService.initialize();
	}

	@Override
	public Mono<Void> cleanUp() {
		log.info("Processing demo cleanup");
		return demoService.cleanUp();
	}

}
