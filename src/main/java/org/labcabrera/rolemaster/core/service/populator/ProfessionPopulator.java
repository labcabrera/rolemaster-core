package org.labcabrera.rolemaster.core.service.populator;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.repository.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProfessionPopulator implements ApplicationRunner {

	@Autowired
	private ProfessionRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		repository.count().doOnSuccess(count -> {
			if (count == 0L) {
				populate();
			}
		}).subscribe();
	}

	public void populate() {
		log.info("Populating professions");
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data/populator/professions.json")) {
			List<Profession> values = objectMapper.readerFor(new TypeReference<List<Profession>>() {
			}).readValue(in);
			values.stream().forEach(e -> e.setMetadata(EntityMetadata.builder()
				.created(LocalDateTime.now())
				.build()));
			repository.saveAll(values).subscribe();
		}
		catch (Exception ex) {
			log.error("Error populating professions", ex);
		}
	}
}
