package org.labcabrera.rolemaster.core.service.populator;

import java.io.InputStream;
import java.util.ArrayList;
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
		List<String> resources = getResources();
		List<E> values = collectValues();
		repository
			.deleteAll()
			.thenMany(Flux.fromIterable(values))
			.flatMap(repository::save)
			.then(Mono.just(String.format("Inserted %s entities from %s", values.size(), resources)))
			.subscribe(log::info);
	}

	protected List<E> collectValues() {
		List<E> list = new ArrayList<>();
		TypeReference<List<E>> typeReference = getTypeReference();
		getResources().stream().forEach(resource -> {
			try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
				List<E> values = objectMapper.readerFor(typeReference).readValue(in);
				list.addAll(values);
			}
			catch (Exception ex) {
				log.error("Error reading {}", resource, ex);
			}
		});
		return list;
	}

	protected abstract List<String> getResources();

	protected abstract TypeReference<List<E>> getTypeReference();
}
