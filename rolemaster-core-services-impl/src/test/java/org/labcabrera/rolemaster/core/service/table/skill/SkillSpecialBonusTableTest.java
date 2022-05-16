package org.labcabrera.rolemaster.core.service.table.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class SkillSpecialBonusTableTest {

	private SkillSpecialBonusTable table = new SkillSpecialBonusTable();

	private List<Integer> mask = Arrays.asList(6, 5, 4, 3);

	@Test
	void test00() {
		assertEquals(0, table.apply(0, mask));
	}

	@Test
	void test01() {
		assertEquals(6, table.apply(1, mask));
	}

	@Test
	void test10() {
		assertEquals(60, table.apply(10, mask));
	}

	@Test
	void test11() {
		assertEquals(65, table.apply(11, mask));
	}

	@Test
	void test20() {
		assertEquals(110, table.apply(20, mask));
	}

	@Test
	void test30() {
		assertEquals(150, table.apply(30, mask));
	}

	@Test
	void test31() {
		assertEquals(153, table.apply(31, mask));
	}

}
