package org.labcabrera.rolemaster.core.model.validation;

public final class ValidationConstants {

	public static final String INVALID_TACTICAL_ACTION = "{invalid.tactical.action}";
	public static final String INVALID_TACTICAL_ACTION_SNAP_PERCENT = "{invalid.tactical.action.snap.percent}";
	public static final String INVALID_TACTICAL_ACTION_NORMAL_PERCENT = "{invalid.tactical.action.normal.percent}";

	public static final String INVALID_ATTACK_MELEE = "{invalid.attack.melee}";
	public static final String INVALID_ATTACK_MELEE_NOT_REQUIRED_TARGET = "{invalid.attack.melee.not-required-target}";
	public static final String INVALID_ATTACK_MELEE_REQUIRED_TARGET = "{invalid.attack.melee.required-target}";
	public static final String INVALID_ATTACK_SOURCE_EQUALS_TARGET = "{invalid.attack.melee.source-cannot-be-the-same-as-target}";
	public static final String INVALID_ATTACK_MELEE_INVALID_ROLL_COUNT = "{invalid.attack.melee.invalid-roll-count}";

	public static final String INVALID_ACTION_SOURCE_NOT_FOUND = "{invalid.action.source.not-found}";

	public static final String INVALID_SKILL_UPGRADE = "{invalid.skill.upgrade}";
	public static final String INVALID_SKILL_UPGRADE_REQUIRED_SKILLS = "{invalid.skill.upgrade.required-at-least-one-skill-or-category}";

	private ValidationConstants() {
	}

}
