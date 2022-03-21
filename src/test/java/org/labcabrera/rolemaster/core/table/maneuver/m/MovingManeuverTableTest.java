package org.labcabrera.rolemaster.core.table.maneuver.m;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovingManeuverTableTest {

	@Autowired
	private MovingManeuverTable table;

	@Test
	void testM202() {
		MovingManeuverEntry e = table.get(ManeuverDificulty.ROUTINE, -202);
		assertNotNull(e);
		assertEquals(-2, e.getHp());
		assertNull(e.getResult());
	}

	@Test
	void test276() {
		MovingManeuverEntry e = table.get(ManeuverDificulty.ROUTINE, 276);
		assertNotNull(e);
		assertNotNull(e.getText());
		assertNull(e.getResult());
	}

	@Test
	void test01() {
		MovingManeuverEntry e = table.get(ManeuverDificulty.ROUTINE, 1);
		assertNotNull(e);
		assertEquals(80, e.getResult());
	}

	@Test
	void test116() {
		MovingManeuverEntry e = table.get(ManeuverDificulty.ROUTINE, 116);
		assertNotNull(e);
		assertEquals(120, e.getResult());
	}

}
