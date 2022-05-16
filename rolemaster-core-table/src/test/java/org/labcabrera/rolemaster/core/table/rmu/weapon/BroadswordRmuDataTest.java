package org.labcabrera.rolemaster.core.table.rmu.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.table.Table3DIntRange;
import org.labcabrera.rolemaster.core.table.Table3DIntRangeLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BroadswordRmuDataTest {

	@Autowired
	private Table3DIntRangeLoader tableLoader;

	private Table3DIntRange<String> table;

	@Test
	void test() {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data/table/rmu/weapon/broadsword-rmu.csv");
		table = tableLoader.load(in, "-", e -> e);

		assertEquals("23FS", table.apply(200, 1));
		assertEquals("23FS", table.apply(175, 1));
		assertEquals("23FS", table.apply(173, 1));
		assertEquals("22FS", table.apply(172, 1));
		assertEquals("2", table.apply(71, 1));
		assertEquals("0", table.apply(70, 1));
		assertEquals("0", table.apply(-100, 1));

		assertEquals("14DS", table.apply(143, 3));

		assertEquals("5AK", table.apply(143, 10));

		assertEquals("5", table.apply(142, 10));
		assertEquals("0", table.apply(65, 10));
	}

}
