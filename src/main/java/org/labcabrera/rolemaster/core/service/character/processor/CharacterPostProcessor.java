package org.labcabrera.rolemaster.core.service.character.processor;

import java.util.function.Consumer;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;

interface CharacterPostProcessor extends Consumer<CharacterInfo> {

	static final class Orders {
		static final int ATTRIBUTE = 200;

		static final int SKILL_CATEGORY = 210;

		static final int SKILL = 220;

		static final int RESISTANCE = 227;
		static final int HP = 229;
		static final int BASE_MOVEMENT = 230;
		static final int DEV_POINTS = 231;
		static final int EXHAUSTION_POINTS = 240;

		private Orders() {
		}

	}

}
