package org.labcabrera.rolemaster.core.service.populator;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RacePopulator implements CommandLineRunner {

	@Autowired
	private RaceRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void run(String... args) throws Exception {
		repository.count().doOnSuccess(count -> {
			if (count == 0L) {
				populate();
			}
		}).subscribe();
	}

	private void populate() {
		log.info("Populating races");
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data/populator/races.json")) {
			List<Race> races = objectMapper.readerFor(new TypeReference<List<Race>>() {
			}).readValue(in);
			races.stream().forEach(e -> e.setMetadata(EntityMetadata.builder()
				.created(LocalDateTime.now())
				.build()));
			repository.saveAll(races).subscribe();
		}
		catch (Exception ex) {
			log.error("Error populating races", ex);
		}
	}

}
