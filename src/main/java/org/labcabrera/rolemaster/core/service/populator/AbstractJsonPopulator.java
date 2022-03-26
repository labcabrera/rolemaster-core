package org.labcabrera.rolemaster.core.service.populator;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class AbstractJsonPopulator<E> implements ApplicationRunner {

	@Autowired
	private ReactiveMongoRepository<E, String> repository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String resource = getResource();
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(getResource())) {

			final List<E> values = objectMapper.readerFor(getTypeReference()).readValue(in);

			repository
				.deleteAll()
				.thenMany(Flux.fromIterable(values))
				.flatMap(repository::save)
				.then(Mono.just(String.format("Inserted %s entities from %s", values.size(), resource)))
				.subscribe(e -> log.info(e));
		}
		catch (Exception ex) {
			log.error("Error populating {}", resource, ex);
		}
	}

	protected abstract String getResource();

	protected abstract TypeReference<List<E>> getTypeReference();
}
