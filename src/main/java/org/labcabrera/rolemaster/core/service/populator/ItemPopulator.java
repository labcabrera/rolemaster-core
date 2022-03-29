package org.labcabrera.rolemaster.core.service.populator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.item.Item;
import org.labcabrera.rolemaster.core.model.item.Weapon;
import org.labcabrera.rolemaster.core.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ItemPopulator implements ApplicationRunner {

	private static final String RESOURCE_WEAPONS = "data/populator/items/weapons.json";
	private static final String RESOURCE_ITEMS = "data/populator/items/items.json";

	@Autowired
	private ItemRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	public void run(ApplicationArguments args) throws Exception {
		List<Item> values = collectValues();
		repository
			.deleteAll()
			.thenMany(Flux.fromIterable(values))
			.flatMap(repository::save)
			.then(Mono.just(String.format("Inserted %s entities from %s",
				values.size(),
				Arrays.asList(RESOURCE_ITEMS, RESOURCE_WEAPONS))))
			.subscribe(log::info);
	}

	protected List<Item> collectValues() throws IOException {
		List<Item> list = new ArrayList<>();

		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCE_ITEMS)) {
			List<Item> values = objectMapper.readerFor(new TypeReference<List<Item>>() {
			}).readValue(in);
			list.addAll(values);
		}

		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCE_WEAPONS)) {
			List<Weapon> values = objectMapper.readerFor(new TypeReference<List<Weapon>>() {
			}).readValue(in);
			list.addAll(values);
		}
		return list;
	}

}