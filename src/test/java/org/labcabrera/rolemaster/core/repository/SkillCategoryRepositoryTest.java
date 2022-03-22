package org.labcabrera.rolemaster.core.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.SkillCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SkillCategoryRepositoryTest {

	@Autowired
	private SkillCategoryRepository repository;

	@Test
	void test() {
		repository.findAll()
			.collectList()
			.subscribe(System.out::println);
	}

	@Test
	void test02() {
		List<SkillCategory> list = repository.findAll()
			.collectList()
			.block();
		list.stream().forEach(System.out::println);
	}

}
