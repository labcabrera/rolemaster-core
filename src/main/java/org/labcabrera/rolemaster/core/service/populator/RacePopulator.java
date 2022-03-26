package org.labcabrera.rolemaster.core.service.populator;

import java.io.InputStream;
import java.util.List;

import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RacePopulator implements CommandLineRunner {

	@Autowired
	private RaceRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void run(String... args) throws Exception {
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data/populator/races.json")) {

			final List<Race> values = objectMapper.readerFor(new TypeReference<List<Race>>() {
			}).readValue(in);

			repository
				.deleteAll()
				.thenMany(Flux.fromIterable(values))
				.flatMap(repository::save)
				.then(Mono.just(String.format("Inserted %s races", values.size())))
				.subscribe(e -> log.info(e));
		}
		catch (Exception ex) {
			log.error("Error populating races", ex);
		}
	}

}
