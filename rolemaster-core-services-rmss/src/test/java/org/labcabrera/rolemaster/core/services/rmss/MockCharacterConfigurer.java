package org.labcabrera.rolemaster.core.services.rmss;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;

public class MockCharacterConfigurer {

	public static void configure(CharacterInfo characterInfo) {
		characterInfo.setName("Kiove");
		characterInfo.setMaxHp(100);
	}

}
