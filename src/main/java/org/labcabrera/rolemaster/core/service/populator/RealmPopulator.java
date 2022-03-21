package org.labcabrera.rolemaster.core.service.populator;

import org.labcabrera.rolemaster.core.model.Realm;
import org.labcabrera.rolemaster.core.repository.RealmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class RealmPopulator implements ApplicationRunner {

	@Autowired
	private RealmRepository repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String[] realms = { "essence", "channeling", "mentalism" };

		repository
			.deleteAll()
			.thenMany(Flux
				.just(realms)
				.map(realm -> {
					return Realm.builder()
						.id(realm)
						.name(StringUtils.capitalize(realm))
						.build();
				})
				.flatMap(repository::save))
			.thenMany(repository.findAll())
			.subscribe(realm -> log.debug("Created realm {}", realm));
	}

}
