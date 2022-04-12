package org.labcabrera.rolemaster.core.model.tactical;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.item.instances.CustomWeapon;

class TacticalCharacterTest {

	@Test
	void test() {
		TacticalCharacter tc = TacticalCharacter.builder().build();

		tc.getItems().setMainWeapon(CustomWeapon.builder()
			.weaponCategoryId("scimitar")
			.bonus(5)
			.build());
		tc.getItems().setOffhandWeapon(CustomWeapon.builder()
			.weaponCategoryId("dagger")
			.build());

		fail("Not yet implemented");
	}

}
