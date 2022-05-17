package org.labcabrera.rolemaster.core.services.commons.populator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.labcabrera.rolemaster.core.model.npc.NpcPredefinedName;
import org.labcabrera.rolemaster.core.repository.NpcPredefinedNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
class NpcPredefinedNameGenerator implements ApplicationRunner {

	@Autowired
	private NpcPredefinedNameRepository repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Map<String, String> groups = new LinkedHashMap<>();
		groups.put("orc", "data/populator/npcs/names/names-orcs.txt");
		groups.put("norse", "data/populator/npcs/names/names-norse-gods.txt");
		groups.put("sindar-male", "data/populator/npcs/names/names-sindar-male.txt");
		List<NpcPredefinedName> entities = new ArrayList<>();
		for (Entry<String, String> entry : groups.entrySet()) {
			String group = entry.getKey();
			String resource = entry.getValue();
			readResource(resource).stream().map(e -> NpcPredefinedName.builder()
				.group(group)
				.name(e)
				.build()).forEach(entities::add);
		}
		repository.deleteAll()
			.thenMany(repository.saveAll(entities))
			.then(Mono.just(String.format("Inserted %s entities from %s", entities.size(), groups.values())))
			.subscribe(log::info);
	}

	private List<String> readResource(String resourceName) {
		List<String> values = new ArrayList<>();
		try (
			InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName);
			Scanner scanner = new Scanner(in);) {
			while (scanner.hasNextLine()) {
				values.add(scanner.nextLine());
			}
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return values;
	}

}
