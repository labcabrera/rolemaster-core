package org.labcabrera.rolemaster.core.services.character;

import java.util.function.Consumer;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;

import lombok.experimental.UtilityClass;

public interface CharacterUpdatePostProcessor extends Consumer<CharacterModificationContext> {

	@UtilityClass
	public static final class Orders {

		public static final int ATTRIBUTE = 100;
		public static final int RACE = 110;
		public static final int PROFESSION = 120;


		public static final int SKILL_CATEGORY = 210;
		public static final int SKILL = 220;
		public static final int RESISTANCE = 227;
		public static final int HP = 229;
		public static final int BASE_MOVEMENT = 230;
		public static final int DEV_POINTS = 231;
		public static final int EXHAUSTION_POINTS = 240;
		public static final int DB = 300;
		public static final int SORT_SKILLS = 1000;

	}

}
